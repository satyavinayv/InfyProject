package com.infy.WebComic_Backend.entity;

//Comment.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @JoinColumn(name = "comic_id", nullable = false)
 private Comic comic;
 
 @ManyToOne
 @JoinColumn(name = "user_id", nullable = false)
 private User user;
 
 @Column(nullable = false, length = 1000)
 private String content;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
 }
}
