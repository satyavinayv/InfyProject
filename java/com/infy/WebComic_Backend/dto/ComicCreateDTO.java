package com.infy.WebComic_Backend.dto;

//ComicCreateDTO.java

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicCreateDTO {
 @NotBlank(message = "Title is required")
 @Size(max = 100, message = "Title must be less than 100 characters")
 private String title;
 
 @NotBlank(message = "Description is required")
 @Size(max = 1000, message = "Description must be less than 1000 characters")
 private String description;
 
 private List<String> genres;
 
 private List<String> tags;
 
 private boolean subscriptionRequired;
 
 private Double subscriptionPrice;
}