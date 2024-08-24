package com.demo.controller;

import com.demo.controller.model.ApiResponse;
import com.demo.controller.model.EmployeeRequest;
import com.demo.entity.EmployeeEntity;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

  private final EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping(value = "/create")
  public ResponseEntity<EmployeeEntity> registerUser(@RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(employeeService.createEmployee(request));
  }

  @PutMapping("/update/{employeeId}")
  public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable String employeeId, @RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(employeeService.updateEmployee(employeeId, request));
  }

  @GetMapping(value = "/get-list-employee")
  public Page<EmployeeEntity> getAllProduct(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return employeeService.getListEmployee(page, size);
  }

  @DeleteMapping("/delete/{employeeId}")
  public ResponseEntity<ApiResponse> deleteEmployee(@PathVariable String employeeId) {
    return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
  }
}