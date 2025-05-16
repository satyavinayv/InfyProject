package com.infy.WebComic_Backend.dto;

//CommentCreateDTO.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDTO {
 @NotBlank(message = "Content is required")
 @Size(max = 1000, message = "Comment must be less than 1000 characters")
 private String content;
}