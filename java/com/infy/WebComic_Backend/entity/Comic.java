package com.infy.WebComic_Backend.entity;

//Comic.java

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comic {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @Column(nullable = false)
 private String title;
 
 @Column(nullable = false, length = 1000)
 private String description;
 
 private String coverImageUrl;
 
 @ManyToOne
 @JoinColumn(name = "creator_id", nullable = false)
 private User creator;
 
 @ManyToMany
 @JoinTable(
     name = "comic_genres",
     joinColumns = @JoinColumn(name = "comic_id"),
     inverseJoinColumns = @JoinColumn(name = "genre_id")
 )
 private Set<Genre> genres = new HashSet<>();
 
 @ManyToMany
 @JoinTable(
     name = "comic_tags",
     joinColumns = @JoinColumn(name = "comic_id"),
     inverseJoinColumns = @JoinColumn(name = "tag_id")
 )
 private Set<Tag> tags = new HashSet<>();
 
 private double rating;
 
 private int views;
 
 private int likes;
 
 private boolean completed;
 
 private boolean subscriptionRequired;
 
 private Double subscriptionPrice;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 private LocalDateTime updatedAt;
 
 @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true)
 @OrderBy("order ASC")
 private List<Chapter> chapters = new ArrayList<>();
 
 @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true)
 private List<Comment> comments = new ArrayList<>();
 
 @PrePersist
 protected void onCreate() {
     createdAt = LocalDateTime.now();
 }
 
 @PreUpdate
 protected void onUpdate() {
     updatedAt = LocalDateTime.now();
 }
}