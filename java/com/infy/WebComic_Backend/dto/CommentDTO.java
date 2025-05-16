package com.infy.WebComic_Backend.dto;

//CommentDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
 private Long id;
 private Long comicId;
 private Long userId;
 private String username;
 private String content;
 private LocalDateTime createdAt;
}
