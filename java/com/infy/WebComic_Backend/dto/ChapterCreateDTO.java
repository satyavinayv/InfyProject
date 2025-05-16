package com.infy.WebComic_Backend.dto;

//ChapterCreateDTO.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterCreateDTO {
 @NotBlank(message = "Title is required")
 @Size(max = 100, message = "Title must be less than 100 characters")
 private String title;
}
