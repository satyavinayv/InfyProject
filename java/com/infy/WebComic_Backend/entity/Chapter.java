package com.infy.WebComic_Backend.entity;

//Chapter.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @JoinColumn(name = "comic_id", nullable = false)
 private Comic comic;
 
 @Column(nullable = false)
 private String title;
 
 @Column(name = "chapter_order", nullable = false)
 private int order;
 
 private String thumbnailUrl;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 private LocalDateTime updatedAt;
 
 @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("pageNumber ASC")
 private List<Page> pages = new ArrayList<>();
 
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
 }
 
 @PreUpdate
 protected void onUpdate() {
     updatedAt = LocalDateTime.now();
 }
}
