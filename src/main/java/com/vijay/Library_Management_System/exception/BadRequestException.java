package com.vijay.Library_Management_System.exception;

//Thrown for invalid operations like returning a book you didn't borrow (400)
public class BadRequestException extends RuntimeException {
 public BadRequestException(String message) {
     super(message);
 }
}
