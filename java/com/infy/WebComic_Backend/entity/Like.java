package com.infy.WebComic_Backend.entity;

//Like.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes", uniqueConstraints = {
 @UniqueConstraint(columnNames = {"user_id", "comic_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @ManyToOne
 @JoinColumn(name = "user_id", nullable = false)
 private User user;
 
 @ManyToOne
 @JoinColumn(name = "comic_id", nullable = false)
 private Comic comic;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
 }
}