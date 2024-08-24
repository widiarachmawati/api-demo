package com.demo.controller;

import com.demo.controller.model.UserRequest;
import com.demo.controller.model.ApiResponse;
import com.demo.entity.UserEntity;
import com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping(value = "/register")
  public ResponseEntity<UserEntity> registerUser(@RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.registerUser(request));
  }

  @PostMapping(value = "/login")
  public ResponseEntity<ApiResponse> loginUser(@RequestBody UserRequest request) {
    return ResponseEntity.ok(userService.loginUser(request));
  }
}