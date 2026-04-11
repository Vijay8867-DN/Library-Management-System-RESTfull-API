package com.vijay.Library_Management_System.service;

import java.util.List;

import com.vijay.Library_Management_System.dto.BookDto;

import jakarta.validation.Valid;



public interface BookService {

	BookDto.Response addBook(BookDto.Request request);

	List<BookDto.Response> getAllBooks();

	List<BookDto.Response> getByLibrary(Long libraryId, String author, String title);

	List<BookDto.Response> getAvailableBooks(Long librayId);

	BookDto.Response getById(Long id);

	BookDto.Response updateBook(Long id, BookDto.Request request);

	void deleteBook(Long id);

		
}
