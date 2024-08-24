package com.demo.service;

import com.demo.controller.model.UserRequest;
import com.demo.controller.model.ApiResponse;
import com.demo.entity.UserEntity;
import com.demo.repository.UserRepository;
import com.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                     JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public UserEntity registerUser(UserRequest request) {
    UserEntity userEntity = UserEntity.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    return userRepository.save(userEntity);
  }

  public ApiResponse loginUser(UserRequest request) {
    Optional<UserEntity> existingUser = userRepository.findByUsername(request.getUsername());
    if (existingUser.isPresent() && passwordEncoder.matches(request.getPassword(), existingUser.get().getPassword())) {
      return ApiResponse.builder()
          .response(jwtUtil.generateToken(request))
          .build();
    }

    return ApiResponse.builder()
        .response("Invalid credentials")
        .build();
  }
}
