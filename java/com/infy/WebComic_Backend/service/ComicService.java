package com.infy.WebComic_Backend.service;

//ComicService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WebComic_Backend.dto.ComicCreateDTO;
import com.infy.WebComic_Backend.dto.ComicDTO;
import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Genre;
import com.infy.WebComic_Backend.entity.Tag;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceNotFoundException;
import com.infy.WebComic_Backend.exception.UnauthorizedException;
import com.infy.WebComic_Backend.repository.ChapterRepository;
import com.infy.WebComic_Backend.repository.ComicRepository;
import com.infy.WebComic_Backend.repository.GenreRepository;
import com.infy.WebComic_Backend.repository.TagRepository;
import com.infy.WebComic_Backend.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ComicService {

 @Autowired
 private ComicRepository comicRepository;

 @Autowired
 private GenreRepository genreRepository;

 @Autowired
 private TagRepository tagRepository;

 @Autowired
 private ChapterRepository chapterRepository;

 @Autowired
 private FileStorageService fileStorageService;
 
 @Autowired
 private UserRepository userRepository;

 @Autowired
 private ModelMapper modelMapper;

 @Transactional
 public ComicDTO createComic(ComicCreateDTO comicCreateDTO) {
     User currentUser = getCurrentUser();
     
     // Check if user is a creator
     if (!currentUser.getRole().equals("ROLE_CREATOR")) {
         throw new UnauthorizedException("Only creators can create comics");
     }
     
     Comic comic = new Comic();
     comic.setTitle(comicCreateDTO.getTitle());
     comic.setDescription(comicCreateDTO.getDescription());
     comic.setCreator(currentUser);
     comic.setSubscriptionRequired(comicCreateDTO.isSubscriptionRequired());
     comic.setSubscriptionPrice(comicCreateDTO.getSubscriptionPrice());
     
     // Process genres
     Set<Genre> genres = new HashSet<>();
     if (comicCreateDTO.getGenres() != null) {
         for (String genreName : comicCreateDTO.getGenres()) {
             Genre genre = genreRepository.findByNameIgnoreCase(genreName)
                     .orElseGet(() -> {
                         Genre newGenre = new Genre();
                         newGenre.setName(genreName);
                         return genreRepository.save(newGenre);
                     });
             genres.add(genre);
         }
     }
     comic.setGenres(genres);
     
     // Process tags
     Set<Tag> tags = new HashSet<>();
     if (comicCreateDTO.getTags() != null) {
         for (String tagName : comicCreateDTO.getTags()) {
             Tag tag = tagRepository.findByNameIgnoreCase(tagName)
                     .orElseGet(() -> {
                         Tag newTag = new Tag();
                         newTag.setName(tagName);
                         return tagRepository.save(newTag);
                     });
             tags.add(tag);
         }
     }
     comic.setTags(tags);
     
     Comic savedComic = comicRepository.save(comic);
     
     return mapComicToDTO(savedComic);
 }

 @Transactional
 public ComicDTO updateComic(Long id, ComicCreateDTO comicUpdateDTO) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + id));
     
     // Check if user is the creator of the comic
     if (!comic.getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only update your own comics");
     }
     
     comic.setTitle(comicUpdateDTO.getTitle());
     comic.setDescription(comicUpdateDTO.getDescription());
     comic.setSubscriptionRequired(comicUpdateDTO.isSubscriptionRequired());
     comic.setSubscriptionPrice(comicUpdateDTO.getSubscriptionPrice());
     
     // Process genres
     Set<Genre> genres = new HashSet<>();
     if (comicUpdateDTO.getGenres() != null) {
         for (String genreName : comicUpdateDTO.getGenres()) {
             Genre genre = genreRepository.findByNameIgnoreCase(genreName)
                     .orElseGet(() -> {
                         Genre newGenre = new Genre();
                         newGenre.setName(genreName);
                         return genreRepository.save(newGenre);
                     });
             genres.add(genre);
         }
     }
     comic.setGenres(genres);
     
     // Process tags
     Set<Tag> tags = new HashSet<>();
     if (comicUpdateDTO.getTags() != null) {
         for (String tagName : comicUpdateDTO.getTags()) {
             Tag tag = tagRepository.findByNameIgnoreCase(tagName)
                     .orElseGet(() -> {
                         Tag newTag = new Tag();
                         newTag.setName(tagName);
                         return tagRepository.save(newTag);
                     });
             tags.add(tag);
         }
     }
     comic.setTags(tags);
     
     Comic updatedComic = comicRepository.save(comic);
     
     return mapComicToDTO(updatedComic);
 }

 public ComicDTO getComicById(Long id) {
     Comic comic = comicRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + id));
     
     // Increment view count
     comic.setViews(comic.getViews() + 1);
     comicRepository.save(comic);
     
     return mapComicToDTO(comic);
 }

 public Page<ComicDTO> getComicsByCreator(Long creatorId, Pageable pageable) {
     User creator = new User();
     creator.setId(creatorId);
     
     Page<Comic> comics = comicRepository.findByCreator(creator, pageable);
     
     return comics.map(this::mapComicToDTO);
 }

 public Page<ComicDTO> searchComics(String query, Pageable pageable) {
     Page<Comic> comics = comicRepository.search(query, pageable);
     
     return comics.map(this::mapComicToDTO);
 }

 public Page<ComicDTO> getComicsByGenre(String genreName, Pageable pageable) {
     Genre genre = genreRepository.findByNameIgnoreCase(genreName)
             .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreName));
     
     Page<Comic> comics = comicRepository.findByGenresContaining(genre, pageable);
     
     return comics.map(this::mapComicToDTO);
 }

 public Page<ComicDTO> getComicsByTag(String tagName, Pageable pageable) {
     Tag tag = tagRepository.findByNameIgnoreCase(tagName)
             .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + tagName));
     
     Page<Comic> comics = comicRepository.findByTagsContaining(tag, pageable);
     
     return comics.map(this::mapComicToDTO);
 }

 public List<ComicDTO> getTrendingComics(int limit) {
     Pageable pageable = Pageable.ofSize(limit);
     List<Comic> comics = comicRepository.findTrendingComics(pageable);
     
     return comics.stream()
             .map(this::mapComicToDTO)
             .collect(Collectors.toList());
 }

 public Page<ComicDTO> getRecentComics(Pageable pageable) {
     Page<Comic> comics = comicRepository.findRecentComics(pageable);
     
     return comics.map(this::mapComicToDTO);
 }

 @Transactional
 public void deleteComic(Long id) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(id)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + id));
     
     // Check if user is the creator of the comic
     if (!comic.getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only delete your own comics");
     }
     
     comicRepository.delete(comic);
 }

 private ComicDTO mapComicToDTO(Comic comic) {
     ComicDTO comicDTO = modelMapper.map(comic, ComicDTO.class);
     comicDTO.setCreatorId(comic.getCreator().getId());
     comicDTO.setCreatorName(comic.getCreator().getUsername());
     comicDTO.setGenres(comic.getGenres().stream().map(Genre::getName).collect(Collectors.toList()));
     comicDTO.setTags(comic.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
     comicDTO.setChapterCount(chapterRepository.countByComic(comic));
     
     return comicDTO;
 }

 private User getCurrentUser() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}