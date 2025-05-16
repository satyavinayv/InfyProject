package com.infy.WebComic_Backend.dto;

//JwtResponseDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO {
 private String token;
 private String type = "Bearer";
 private Long id;
 private String username;
 private String email;
 private String role;
 
 public JwtResponseDTO(String token, Long id, String username, String email, String role) {
     this.token = token;
     this.id = id;
     this.username = username;
     this.email = email;
     this.role = role;
 }
}