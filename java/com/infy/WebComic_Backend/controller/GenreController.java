package com.infy.WebComic_Backend.controller;

//GenreController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.WebComic_Backend.dto.GenreDTO;
import com.infy.WebComic_Backend.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

 @Autowired
 private GenreService genreService;

 @GetMapping
 public ResponseEntity<List<GenreDTO>> getAllGenres() {
     List<GenreDTO> genres = genreService.getAllGenres();
     return ResponseEntity.ok(genres);
 }
}