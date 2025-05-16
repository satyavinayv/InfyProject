package com.infy.WebComic_Backend.controller;

//UserController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infy.WebComic_Backend.dto.UserDTO;
import com.infy.WebComic_Backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

 @Autowired
 private UserService userService;

 @GetMapping("/me")
 public ResponseEntity<UserDTO> getCurrentUser() {
     UserDTO userDTO = userService.getCurrentUser();
     return ResponseEntity.ok(userDTO);
 }

 @GetMapping("/{id}")
 public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
     UserDTO userDTO = userService.getUserById(id);
     return ResponseEntity.ok(userDTO);
 }

 @PostMapping("/{id}/follow")
 public ResponseEntity<?> followUser(@PathVariable Long id) {
     userService.followUser(id);
     return ResponseEntity.ok().build();
 }

 @DeleteMapping("/{id}/follow")
 public ResponseEntity<?> unfollowUser(@PathVariable Long id) {
     userService.unfollowUser(id);
     return ResponseEntity.ok().build();
 }
}
