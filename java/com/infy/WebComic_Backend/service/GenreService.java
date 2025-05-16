package com.infy.WebComic_Backend.service;

//GenreService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.WebComic_Backend.dto.GenreDTO;
import com.infy.WebComic_Backend.entity.Genre;
import com.infy.WebComic_Backend.repository.GenreRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {

 @Autowired
 private GenreRepository genreRepository;

 @Autowired
 private ModelMapper modelMapper;

 public List<GenreDTO> getAllGenres() {
     List<Genre> genres = genreRepository.findAll();
     
     return genres.stream()
             .map(genre -> modelMapper.map(genre, GenreDTO.class))
             .collect(Collectors.toList());
 }
}