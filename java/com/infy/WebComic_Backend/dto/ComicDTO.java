package com.infy.WebComic_Backend.dto;

//ComicDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicDTO {
 private Long id;
 private String title;
 private String description;
 private String coverImageUrl;
 private Long creatorId;
 private String creatorName;
 private List<String> genres;
 private List<String> tags;
 private double rating;
 private int views;
 private int likes;
 private boolean completed;
 private boolean subscriptionRequired;
 private Double subscriptionPrice;
 private LocalDateTime createdAt;
 private LocalDateTime updatedAt;
 private int chapterCount;
}
