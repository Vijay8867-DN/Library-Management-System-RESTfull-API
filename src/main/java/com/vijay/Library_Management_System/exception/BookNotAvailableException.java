package com.vijay.Library_Management_System.exception;

//Thrown when a book exists but has no available copies (422)
public class BookNotAvailableException extends RuntimeException {
	
    public BookNotAvailableException(String message) {
        super(message);
    }
}
