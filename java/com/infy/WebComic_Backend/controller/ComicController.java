package com.infy.WebComic_Backend.controller;

//ComicController.java

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.infy.WebComic_Backend.dto.ComicCreateDTO;
import com.infy.WebComic_Backend.dto.ComicDTO;
import com.infy.WebComic_Backend.service.ComicService;
import com.infy.WebComic_Backend.service.FileStorageService;

import java.util.List;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

 @Autowired
 private ComicService comicService;

 @Autowired
 private FileStorageService fileStorageService;

 @PostMapping
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<ComicDTO> createComic(@Valid @RequestBody ComicCreateDTO comicCreateDTO) {
     ComicDTO comicDTO = comicService.createComic(comicCreateDTO);
     return ResponseEntity.ok(comicDTO);
 }

 @PutMapping("/{id}")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<ComicDTO> updateComic(@PathVariable Long id, @Valid @RequestBody ComicCreateDTO comicUpdateDTO) {
     ComicDTO comicDTO = comicService.updateComic(id, comicUpdateDTO);
     return ResponseEntity.ok(comicDTO);
 }

 @PostMapping("/{id}/cover")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<String> uploadCoverImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
     String fileName = fileStorageService.storeFile(file);
     // Update comic cover image URL
     // This would be implemented in the ComicService
     return ResponseEntity.ok(fileName);
 }

 @GetMapping("/{id}")
 public ResponseEntity<ComicDTO> getComicById(@PathVariable Long id) {
     ComicDTO comicDTO = comicService.getComicById(id);
     return ResponseEntity.ok(comicDTO);
 }

 @GetMapping("/creator/{creatorId}")
 public ResponseEntity<Page<ComicDTO>> getComicsByCreator(@PathVariable Long creatorId, Pageable pageable) {
     Page<ComicDTO> comics = comicService.getComicsByCreator(creatorId, pageable);
     return ResponseEntity.ok(comics);
 }

 @GetMapping("/search")
 public ResponseEntity<Page<ComicDTO>> searchComics(@RequestParam String query, Pageable pageable) {
     Page<ComicDTO> comics = comicService.searchComics(query, pageable);
     return ResponseEntity.ok(comics);
 }

 @GetMapping("/genre/{genreName}")
 public ResponseEntity<Page<ComicDTO>> getComicsByGenre(@PathVariable String genreName, Pageable pageable) {
     Page<ComicDTO> comics = comicService.getComicsByGenre(genreName, pageable);
     return ResponseEntity.ok(comics);
 }

 @GetMapping("/tag/{tagName}")
 public ResponseEntity<Page<ComicDTO>> getComicsByTag(@PathVariable String tagName, Pageable pageable) {
     Page<ComicDTO> comics = comicService.getComicsByTag(tagName, pageable);
     return ResponseEntity.ok(comics);
 }

 @GetMapping("/trending")
 public ResponseEntity<List<ComicDTO>> getTrendingComics(@RequestParam(defaultValue = "5") int limit) {
     List<ComicDTO> comics = comicService.getTrendingComics(limit);
     return ResponseEntity.ok(comics);
 }

 @GetMapping("/recent")
 public ResponseEntity<Page<ComicDTO>> getRecentComics(Pageable pageable) {
     Page<ComicDTO> comics = comicService.getRecentComics(pageable);
     return ResponseEntity.ok(comics);
 }

 @DeleteMapping("/{id}")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<?> deleteComic(@PathVariable Long id) {
     comicService.deleteComic(id);
     return ResponseEntity.ok().build();
 }
}
