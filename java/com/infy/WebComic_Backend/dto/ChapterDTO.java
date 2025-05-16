package com.infy.WebComic_Backend.dto;

//ChapterDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterDTO {
 private Long id;
 private Long comicId;
 private String title;
 private int order;
 private String thumbnailUrl;
 private LocalDateTime createdAt;
 private int pageCount;
}
