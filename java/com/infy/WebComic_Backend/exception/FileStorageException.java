package com.infy.WebComic_Backend.exception;

//FileStorageException.java

public class FileStorageException extends RuntimeException {
 public FileStorageException(String message) {
     super(message);
 }

 public FileStorageException(String message, Throwable cause) {
     super(message, cause);
 }
}
