package com.vijay.Library_Management_System.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.dto.UserDto.Request;
import com.vijay.Library_Management_System.dto.UserDto.Response;
import com.vijay.Library_Management_System.entity.Library;
import com.vijay.Library_Management_System.entity.User;
import com.vijay.Library_Management_System.exception.DuplicateResourceException;
import com.vijay.Library_Management_System.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;
	private final LibraryService libraryService;
	private final BookService bookService;
	@Override
	public Response registerUser(Request request) {
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException(
                    "A user with email '" + request.getEmail() + "' is already registered.");
		}
		
		Library library = libraryService.findById(request.getLibraryId());
		
		User user = User.builder()
						.name(request.getName())
						.email(request.getEmail())
						.phone(request.getPhone())
						.library(library)
						.build();
		return toResponse(userRepo.save(user));
	}
	
	public UserDto.Response toResponse(User user){
		List<BookDto.Response> borrowed = user.getBorrowedBooks() == null
				? List.of()
				: user.getBorrowedBooks().stream()
					.map(bookService::toResponse)
					.collect(Collectors.toList());
		
		return UserDto.Response.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.libraryId(user.getLibrary().getId())
				.libraryName(user.getLibrary().getName())
				.borrowedBooks(borrowed)
				.createdAt(user.getCreatedAt())
				.build();
	}

	@Override
	public List<UserDto.Response> getAllUsers() {
		
		return userRepo.findAll().stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

}
