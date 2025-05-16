package com.infy.WebComic_Backend.dto;

//PageDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO {
 private Long id;
 private Long chapterId;
 private int pageNumber;
 private String imageUrl;
}
