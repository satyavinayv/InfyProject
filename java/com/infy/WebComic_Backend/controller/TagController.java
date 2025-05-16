package com.infy.WebComic_Backend.controller;

//TagController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.WebComic_Backend.dto.TagDTO;
import com.infy.WebComic_Backend.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

 @Autowired
 private TagService tagService;

 @GetMapping
 public ResponseEntity<List<TagDTO>> getAllTags() {
     List<TagDTO> tags = tagService.getAllTags();
     return ResponseEntity.ok(tags);
 }
}
