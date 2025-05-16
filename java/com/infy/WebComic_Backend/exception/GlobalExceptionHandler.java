package com.infy.WebComic_Backend.exception;

//GlobalExceptionHandler.java

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

 @ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
     ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
 }

 @ExceptionHandler(ResourceAlreadyExistsException.class)
 public ResponseEntity<?> resourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
     ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
 }

 @ExceptionHandler(UnauthorizedException.class)
 public ResponseEntity<?> unauthorizedException(UnauthorizedException ex, WebRequest request) {
     ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
 }

 @ExceptionHandler(FileStorageException.class)
 public ResponseEntity<?> fileStorageException(FileStorageException ex, WebRequest request) {
     ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
 }

 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
     Map<String, String> errors = new HashMap<>();
     ex.getBindingResult().getAllErrors().forEach((error) -> {
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
     });
     return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
 }

 @ExceptionHandler(Exception.class)
 public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
     ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
     return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
 }

 public static class ErrorDetails {
     private Date timestamp;
     private String message;
     private String details;

     public ErrorDetails(Date timestamp, String message, String details) {
         this.timestamp = timestamp;
         this.message = message;
         this.details = details;
     }

     public Date getTimestamp() {
         return timestamp;
     }

     public String getMessage() {
         return message;
     }

     public String getDetails() {
         return details;
     }
 }
}
