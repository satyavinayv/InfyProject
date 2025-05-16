package com.infy.WebComic_Backend.dto;

//ChapterReorderDTO.java

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChapterReorderDTO {
 private List<ChapterOrderItem> chapterOrders;
 
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
 public static class ChapterOrderItem {
     private Long id;
     private int order;
 }
}
