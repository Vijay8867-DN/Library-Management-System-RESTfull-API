package com.vijay.Library_Management_System.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vijay.Library_Management_System.dto.ApiResponse;
import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.dto.BookDto.Response;
import com.vijay.Library_Management_System.entity.Book;
import com.vijay.Library_Management_System.entity.Library;
import com.vijay.Library_Management_System.entity.User;
import com.vijay.Library_Management_System.exception.BadRequestException;
import com.vijay.Library_Management_System.exception.BookNotAvailableException;
import com.vijay.Library_Management_System.exception.DuplicateResourceException;
import com.vijay.Library_Management_System.exception.ResourceNotFoundException;
import com.vijay.Library_Management_System.repository.BookRepo;
import com.vijay.Library_Management_System.repository.UserRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

	private final BookRepo bookRepo;
	private final LibraryService libraryService;
	private final UserRepo userRepo;
	
	@Override
	@Transactional
	public BookDto.Response addBook(BookDto.Request request) {
		if(bookRepo.existsByIsbn(request.getIsbn())) {
			throw new DuplicateResourceException(
					"A book with ISBN '" + request.getIsbn() + "' already exists.");
		}
		
		Library library = libraryService.findById(request.getLibraryId());
		
		Book book = Book.builder()
					.title(request.getTitle())
					.author(request.getAuthor())
					.isbn(request.getIsbn())
					.genre(request.getGenre())
					.available(true)
					.library(library)
					.build();
		
		return toResponse(bookRepo.save(book));
	}
	
	public BookDto.Response toResponse(Book book){
		return BookDto.Response.builder()
				.id(book.getId())
				.title(book.getTitle())
				.author(book.getAuthor())
				.isbn(book.getIsbn())
				.genre(book.getGenre())
				.available(book.getAvailable())
				.libraryId(book.getLibrary().getId())
				.libraryName(book.getLibrary().getName())
				.borrowedByUserId(book.getBorrowedBy() != null ? book.getBorrowedBy().getId() : null )
				.borrowedByUserName(book.getBorrowedBy() != null ? book.getBorrowedBy().getName() : null)
				.createdAt(book.getCreatedAt())
				.build();
	}

	@Override
	@Transactional
	public List<Response> getAllBooks() {
		return bookRepo.findAll()
					.stream()
					.map(this::toResponse)
					.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<BookDto.Response> getByLibrary(Long libraryId, String author, String title) {
		List<BookDto.Response> book;
		
		if(author != null && !author.isBlank()) {
			book = searchByAuthor(libraryId,author);
		}else if(title != null && !title.isBlank()) {
			book = searchByTitel(libraryId, title);
		}else {
			libraryService.findById(libraryId); // validate library exists
			book = bookRepo.findByLibraryId(libraryId)
							.stream()
							.map(this::toResponse)
							.collect(Collectors.toList());
		}
		
		return book;
	}

	private List<BookDto.Response> searchByTitel(Long libraryId, String title) {
		libraryService.findById(libraryId);
		return bookRepo.findByLibraryIdAndTitleContainingIgnoreCase(libraryId,title)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	private List<BookDto.Response> searchByAuthor(Long libraryId, String author) {
		libraryService.findById(libraryId);
		return bookRepo.findByLibraryIdAndAuthorContainingIgnoreCase(libraryId,author)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<Response> getAvailableBooks(Long libraryId) {
		libraryService.findById(libraryId);
		return bookRepo.findByLibraryIdAndAvailable(libraryId, true)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	@Override
	public BookDto.Response getById(Long id) {
		
		return toResponse(findById(id));
	}

	
	public Book findById(Long id) {
		return bookRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Book not found with ID: " + id));
	}

	@Override
	public Response updateBook(Long id, BookDto.Request request) {
		Book book = findById(id);
		
		if(!book.getIsbn().equals(request.getIsbn()) && 
				bookRepo.existsByIsbn(request.getIsbn())) {
			 throw new DuplicateResourceException(
	                    "Another book with ISBN '" + request.getIsbn() + "' already exists.");
		}
		
		if(!book.getLibrary().getId().equals(request.getLibraryId())){
			Library library = libraryService.findById(request.getLibraryId());
			book.setLibrary(library);
		}
		
		book.setTitle(request.getTitle());
		book.setAuthor(request.getAuthor());
		book.setIsbn(request.getIsbn());
		book.setGenre(request.getGenre());
		
		return toResponse(book);
	}

	@Override
	public void deleteBook(Long id) {
		Book book = findById(id);
		
		if(!book.getAvailable()) {
			throw new BadRequestException(
                    "Cannot delete book '" + book.getTitle() + "' — it is currently borrowed.");
		}
		
		bookRepo.deleteById(id);
		
	}

	@Override
	public Response assignBookToUser(Long bookId, Long userId) {
		Book book = findById(bookId);
		
		if(!book.getAvailable()) {
			throw new BookNotAvailableException(
					"Book '"+book.getTitle()+"' is not available - it is already borrowed.");
		}
		
		User user = userRepo.findById(userId)
					.orElseThrow(()-> new ResourceNotFoundException(
							"User not found with the Id: "+userId));
		
		if(!book.getLibrary().getId().equals(user.getLibrary().getId())) {
			throw new BadRequestException(
                    "Book and user must belong to the same library. "
                    + "Book is in library ID " + book.getLibrary().getId()
                    + " but user is registered in library ID " + user.getLibrary().getId() + ".");
		}
		
		book.setAvailable(false);
		book.setBorrowedBy(user);
		
		return toResponse(bookRepo.save(book));
	}

	@Override
	public Response returnBook(Long bookId, Long userId) {
		Book book = findById(bookId);
		
		if(book.getAvailable() || book.getBorrowedBy() == null) {
			throw new BadRequestException(
					 "Book '" + book.getTitle() + "' is not currently borrowed.");
		}
		
		if(!book.getBorrowedBy().getId().equals(userId)) {
			throw new BadRequestException(
                    "Book '" + book.getTitle() + "' was not borrowed by user ID " + userId + ".");
		}
		
		book.setAvailable(true);
		book.setBorrowedBy(null);
		return toResponse(bookRepo.save(book));
	}

	

}
