package com.vijay.Library_Management_System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.Library_Management_System.dto.ApiResponse;
import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.service.BookService;
import com.vijay.Library_Management_System.service.LibraryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<BookDto.Response>> addBook(@Valid
			@RequestBody BookDto.Request request){
		BookDto.Response created = bookService.addBook(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Book added to Library successfully",created));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<BookDto.Response>>> getAllBooks(){
		return ResponseEntity
				.ok(ApiResponse.success(bookService.getAllBooks()));
	}
	
	@GetMapping("/library/{libraryId}")
	public ResponseEntity<ApiResponse<List<BookDto.Response>>> getBooksByLibrary(
			@PathVariable Long libraryId,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) String title){
		
		return ResponseEntity
				.ok(ApiResponse.success(bookService.getByLibrary(libraryId,author,title)));
	}
	
	@GetMapping("/library/{libraryId}/available")
	public ResponseEntity<ApiResponse<List<BookDto.Response>>> getAvailable(
			@PathVariable Long libraryId){
		return ResponseEntity
				.ok(ApiResponse.success(bookService.getAvailableBooks(libraryId)));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BookDto.Response>> getById(@PathVariable Long id){
		
		return ResponseEntity
				.ok(ApiResponse.success(bookService.getById(id)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BookDto.Response>> update(
			@PathVariable Long id,
			@Valid @RequestBody BookDto.Request request){
		
		return ResponseEntity
				.ok(ApiResponse.success("Book updated successfully",bookService.updateBook(id,request)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
	     bookService.deleteBook(id);
	     
	     return ResponseEntity
	    		 .ok(ApiResponse.success("Book deleted Successfully",null));
	}
		
}
