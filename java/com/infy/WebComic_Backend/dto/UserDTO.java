package com.infy.WebComic_Backend.dto;

//UserDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
 private Long id;
 private String username;
 private String email;
 private String role;
 private String bio;
 private String avatarUrl;
 private LocalDateTime createdAt;
 private int followersCount;
 private int followingCount;
}