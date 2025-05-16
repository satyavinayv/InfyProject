package com.infy.WebComic_Backend.controller;

//ChapterController.java

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.infy.WebComic_Backend.dto.ChapterCreateDTO;
import com.infy.WebComic_Backend.dto.ChapterDTO;
import com.infy.WebComic_Backend.dto.ChapterReorderDTO;
import com.infy.WebComic_Backend.dto.PageDTO;
import com.infy.WebComic_Backend.service.ChapterService;

import java.util.List;

@RestController
@RequestMapping("/api/chapters")
public class ChapterController {

 @Autowired
 private ChapterService chapterService;

 @PostMapping("/comic/{comicId}")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<ChapterDTO> createChapter(@PathVariable Long comicId, @Valid @RequestBody ChapterCreateDTO chapterCreateDTO) {
     ChapterDTO chapterDTO = chapterService.createChapter(comicId, chapterCreateDTO);
     return ResponseEntity.ok(chapterDTO);
 }

 @PostMapping("/{chapterId}/pages")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<PageDTO> addPageToChapter(
         @PathVariable Long chapterId,
         @RequestParam("file") MultipartFile file,
         @RequestParam("pageNumber") int pageNumber) {
     PageDTO pageDTO = chapterService.addPageToChapter(chapterId, file, pageNumber);
     return ResponseEntity.ok(pageDTO);
 }

 @GetMapping("/{chapterId}")
 public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Long chapterId) {
     ChapterDTO chapterDTO = chapterService.getChapterById(chapterId);
     return ResponseEntity.ok(chapterDTO);
 }

 @GetMapping("/comic/{comicId}")
 public ResponseEntity<List<ChapterDTO>> getChaptersByComicId(@PathVariable Long comicId) {
     List<ChapterDTO> chapters = chapterService.getChaptersByComicId(comicId);
     return ResponseEntity.ok(chapters);
 }

 @GetMapping("/{chapterId}/pages")
 public ResponseEntity<List<PageDTO>> getPagesByChapterId(@PathVariable Long chapterId) {
     List<PageDTO> pages = chapterService.getPagesByChapterId(chapterId);
     return ResponseEntity.ok(pages);
 }

 @PutMapping("/comic/{comicId}/reorder")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<List<ChapterDTO>> reorderChapters(
         @PathVariable Long comicId,
         @Valid @RequestBody ChapterReorderDTO reorderDTO) {
     List<ChapterDTO> chapters = chapterService.reorderChapters(comicId, reorderDTO);
     return ResponseEntity.ok(chapters);
 }

 @DeleteMapping("/{chapterId}")
 @PreAuthorize("hasRole('CREATOR')")
 public ResponseEntity<?> deleteChapter(@PathVariable Long chapterId) {
     chapterService.deleteChapter(chapterId);
     return ResponseEntity.ok().build();
 }
}
