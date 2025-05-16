package com.infy.WebComic_Backend.controller;

//CommentController.java

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.infy.WebComic_Backend.dto.CommentCreateDTO;
import com.infy.WebComic_Backend.dto.CommentDTO;
import com.infy.WebComic_Backend.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

 @Autowired
 private CommentService commentService;

 @PostMapping("/comic/{comicId}")
 public ResponseEntity<CommentDTO> createComment(
         @PathVariable Long comicId,
         @Valid @RequestBody CommentCreateDTO commentCreateDTO) {
     CommentDTO commentDTO = commentService.createComment(comicId, commentCreateDTO);
     return ResponseEntity.ok(commentDTO);
 }

 @GetMapping("/comic/{comicId}")
 public ResponseEntity<Page<CommentDTO>> getCommentsByComicId(
         @PathVariable Long comicId,
         Pageable pageable) {
     Page<CommentDTO> comments = commentService.getCommentsByComicId(comicId, pageable);
     return ResponseEntity.ok(comments);
 }

 @DeleteMapping("/{commentId}")
 public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
     commentService.deleteComment(commentId);
     return ResponseEntity.ok().build();
 }
}
