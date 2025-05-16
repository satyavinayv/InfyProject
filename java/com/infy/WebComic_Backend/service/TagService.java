package com.infy.WebComic_Backend.service;

//TagService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.WebComic_Backend.dto.TagDTO;
import com.infy.WebComic_Backend.entity.Tag;
import com.infy.WebComic_Backend.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

 @Autowired
 private TagRepository tagRepository;

 @Autowired
 private ModelMapper modelMapper;

 public List<TagDTO> getAllTags() {
     List<Tag> tags = tagRepository.findAll();
     
     return tags.stream()
             .map(tag -> modelMapper.map(tag, TagDTO.class))
             .collect(Collectors.toList());
 }
}
