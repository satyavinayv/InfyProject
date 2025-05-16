package com.infy.WebComic_Backend.service;

//ChapterService.java

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.infy.WebComic_Backend.dto.ChapterCreateDTO;
import com.infy.WebComic_Backend.dto.ChapterDTO;
import com.infy.WebComic_Backend.dto.ChapterReorderDTO;
import com.infy.WebComic_Backend.dto.PageDTO;
import com.infy.WebComic_Backend.entity.Chapter;
import com.infy.WebComic_Backend.entity.Comic;
import com.infy.WebComic_Backend.entity.Page;
import com.infy.WebComic_Backend.entity.User;
import com.infy.WebComic_Backend.exception.ResourceNotFoundException;
import com.infy.WebComic_Backend.exception.UnauthorizedException;
import com.infy.WebComic_Backend.repository.ChapterRepository;
import com.infy.WebComic_Backend.repository.ComicRepository;
import com.infy.WebComic_Backend.repository.PageRepository;
import com.infy.WebComic_Backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService {

 @Autowired
 private ChapterRepository chapterRepository;

 @Autowired
 private ComicRepository comicRepository;

 @Autowired
 private PageRepository pageRepository;

 @Autowired
 private UserRepository userRepository;

 @Autowired
 private FileStorageService fileStorageService;

 @Autowired
 private ModelMapper modelMapper;

 @Transactional
 public ChapterDTO createChapter(Long comicId, ChapterCreateDTO chapterCreateDTO) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     // Check if user is the creator of the comic
     if (!comic.getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only add chapters to your own comics");
     }
     
     // Create new chapter
     Chapter chapter = new Chapter();
     chapter.setComic(comic);
     chapter.setTitle(chapterCreateDTO.getTitle());
     
     // Set order to be the next available order
     int nextOrder = chapterRepository.countByComic(comic) + 1;
     chapter.setOrder(nextOrder);
     
     Chapter savedChapter = chapterRepository.save(chapter);
     
     return mapChapterToDTO(savedChapter);
 }

 @Transactional
 public PageDTO addPageToChapter(Long chapterId, MultipartFile imageFile, int pageNumber) {
     User currentUser = getCurrentUser();
     
     Chapter chapter = chapterRepository.findById(chapterId)
             .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + chapterId));
     
     // Check if user is the creator of the comic
     if (!chapter.getComic().getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only add pages to your own chapters");
     }
     
     // Upload image file
     String imageUrl = fileStorageService.storeFile(imageFile);
     
     // Create new page
     Page page = new Page();
     page.setChapter(chapter);
     page.setPageNumber(pageNumber);
     page.setImageUrl(imageUrl);
     
     Page savedPage = pageRepository.save(page);
     
     // If this is the first page, use it as the chapter thumbnail
     if (pageNumber == 1 && chapter.getThumbnailUrl() == null) {
         chapter.setThumbnailUrl(imageUrl);
         chapterRepository.save(chapter);
     }
     
     return modelMapper.map(savedPage, PageDTO.class);
 }

 public ChapterDTO getChapterById(Long chapterId) {
     Chapter chapter = chapterRepository.findById(chapterId)
             .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + chapterId));
     
     return mapChapterToDTO(chapter);
 }

 public List<ChapterDTO> getChaptersByComicId(Long comicId) {
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     List<Chapter> chapters = chapterRepository.findByComicOrderByOrderAsc(comic);
     
     return chapters.stream()
             .map(this::mapChapterToDTO)
             .collect(Collectors.toList());
 }

 public List<PageDTO> getPagesByChapterId(Long chapterId) {
     Chapter chapter = chapterRepository.findById(chapterId)
             .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + chapterId));
     
     List<Page> pages = pageRepository.findByChapterOrderByPageNumberAsc(chapter);
     
     return pages.stream()
             .map(page -> modelMapper.map(page, PageDTO.class))
             .collect(Collectors.toList());
 }

 @Transactional
 public List<ChapterDTO> reorderChapters(Long comicId, ChapterReorderDTO reorderDTO) {
     User currentUser = getCurrentUser();
     
     Comic comic = comicRepository.findById(comicId)
             .orElseThrow(() -> new ResourceNotFoundException("Comic not found with id: " + comicId));
     
     // Check if user is the creator of the comic
     if (!comic.getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only reorder chapters of your own comics");
     }
     
     // Update chapter orders
     for (ChapterReorderDTO.ChapterOrderItem item : reorderDTO.getChapterOrders()) {
         Chapter chapter = chapterRepository.findById(item.getId())
                 .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + item.getId()));
         
         // Ensure the chapter belongs to the specified comic
         if (!chapter.getComic().getId().equals(comicId)) {
             throw new IllegalArgumentException("Chapter does not belong to the specified comic");
         }
         
         chapter.setOrder(item.getOrder());
         chapterRepository.save(chapter);
     }
     
     // Get updated chapters
     List<Chapter> updatedChapters = chapterRepository.findByComicOrderByOrderAsc(comic);
     
     return updatedChapters.stream()
             .map(this::mapChapterToDTO)
             .collect(Collectors.toList());
 }

 @Transactional
 public void deleteChapter(Long chapterId) {
     User currentUser = getCurrentUser();
     
     Chapter chapter = chapterRepository.findById(chapterId)
             .orElseThrow(() -> new ResourceNotFoundException("Chapter not found with id: " + chapterId));
     
     // Check if user is the creator of the comic
     if (!chapter.getComic().getCreator().getId().equals(currentUser.getId())) {
         throw new UnauthorizedException("You can only delete your own chapters");
     }
     
     chapterRepository.delete(chapter);
     
     // Reorder remaining chapters
     Comic comic = chapter.getComic();
     List<Chapter> remainingChapters = chapterRepository.findByComicOrderByOrderAsc(comic);
     
     int order = 1;
     for (Chapter remainingChapter : remainingChapters) {
         remainingChapter.setOrder(order++);
         chapterRepository.save(remainingChapter);
     }
 }

 private ChapterDTO mapChapterToDTO(Chapter chapter) {
     ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
     chapterDTO.setComicId(chapter.getComic().getId());
     chapterDTO.setPageCount(chapter.getPages().size());
     
     return chapterDTO;
 }

 private User getCurrentUser() {
     UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     return userRepository.findByEmail(userDetails.getUsername())
             .orElseThrow(() -> new ResourceNotFoundException("User not found"));
 }
}