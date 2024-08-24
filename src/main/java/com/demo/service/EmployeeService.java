package com.demo.service;

import com.demo.controller.model.ApiResponse;
import com.demo.controller.model.EmployeeRequest;
import com.demo.entity.EmployeeEntity;
import com.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public EmployeeEntity createEmployee(EmployeeRequest request) {
    return employeeRepository.save(EmployeeEntity.builder()
        .employeeId(request.getEmployeeId())
        .employeeName(request.getEmployeeName())
        .position(request.getPosition())
        .username(request.getUsername())
        .build());
  }

  public EmployeeEntity updateEmployee(String employeeId, EmployeeRequest request) {
    EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
        .orElseThrow(() -> new RuntimeException("Employee not found"));

    employeeEntity.setEmployeeName(request.getEmployeeName());
    employeeEntity.setPosition(request.getPosition());
    employeeEntity.setUsername(request.getUsername());

    return employeeRepository.save(employeeEntity);
  }

  public Page<EmployeeEntity> getListEmployee(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return employeeRepository.findAllByOrderByEmployeeNameAsc(pageable);
  }

  public ApiResponse deleteEmployee(String employeeId) {
    employeeRepository.deleteById(employeeId);
    return ApiResponse.builder()
        .response(String.format("Success Delete Data Employee with employeeId : %s", employeeId))
        .build();
  }

}
