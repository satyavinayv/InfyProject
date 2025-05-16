package com.infy.WebComic_Backend.controller;

//AuthController.java

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infy.WebComic_Backend.dto.JwtResponseDTO;
import com.infy.WebComic_Backend.dto.LoginDTO;
import com.infy.WebComic_Backend.dto.UserDTO;
import com.infy.WebComic_Backend.dto.UserRegistrationDTO;
import com.infy.WebComic_Backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

 @Autowired
 private AuthService authService;

 @PostMapping("/register")
 public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
     UserDTO userDTO = authService.registerUser(registrationDTO);
     return ResponseEntity.ok(userDTO);
 }

 @PostMapping("/login")
 public ResponseEntity<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
     JwtResponseDTO jwtResponse = authService.authenticateUser(loginDTO);
     return ResponseEntity.ok(jwtResponse);
 }
}
