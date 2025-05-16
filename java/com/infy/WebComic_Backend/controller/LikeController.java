package com.infy.WebComic_Backend.controller;

//LikeController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infy.WebComic_Backend.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

 @Autowired
 private LikeService likeService;

 @PostMapping("/comic/{comicId}")
 public ResponseEntity<?> likeComic(@PathVariable Long comicId) {
     likeService.likeComic(comicId);
     return ResponseEntity.ok().build();
 }

 @DeleteMapping("/comic/{comicId}")
 public ResponseEntity<?> unlikeComic(@PathVariable Long comicId) {
     likeService.unlikeComic(comicId);
     return ResponseEntity.ok().build();
 }

 @GetMapping("/comic/{comicId}/status")
 public ResponseEntity<Boolean> hasUserLikedComic(@PathVariable Long comicId) {
     boolean hasLiked = likeService.hasUserLikedComic(comicId);
     return ResponseEntity.ok(hasLiked);
 }
}