package com.vijay.Library_Management_System.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.dto.UserDto.Request;
import com.vijay.Library_Management_System.dto.UserDto.Response;
import com.vijay.Library_Management_System.entity.Library;
import com.vijay.Library_Management_System.entity.User;
import com.vijay.Library_Management_System.exception.BadRequestException;
import com.vijay.Library_Management_System.exception.DuplicateResourceException;
import com.vijay.Library_Management_System.exception.ResourceNotFoundException;
import com.vijay.Library_Management_System.repository.UserRepo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepo userRepo;
	private final LibraryService libraryService;
	private final BookService bookService;
	
	//Register a user into a library
	@Transactional
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
	
	

	//Get all users
	@Transactional(readOnly = true)
	public List<UserDto.Response> getAllUsers() {
		
		return userRepo.findAll().stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}

	//Get users registered in a specific library
    @Transactional(readOnly = true)
	public List<UserDto.Response> getUserByLibraryId(Long libraryId) {
		Library  library = libraryService.findById(libraryId); // validate library exists
		
		return userRepo.findByLibraryId(libraryId)
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
				
	}
	
	

	  //Get single user
	  @Transactional(readOnly = true)
	  public UserDto.Response getUserById(Long userId) {
		
		return toResponse(findById(userId));
	  }

	  //Get borrowed books for a user
	  @Transactional(readOnly = true)
	  public List<BookDto.Response> getBorrowedBooks(Long id) {
		User user = findById(id);
		
		return user.getBorrowedBooks()
					.stream()
					.map(bookService::toResponse)
					.collect(Collectors.toList());
	  }

	  //Update user
	  @Transactional
	  public UserDto.Response updateUser(Long id, @Valid UserDto.Request request) {
		User user = findById(id);
		
		if(!user.getEmail().equals(request.getEmail()) && userRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException(
					"Another user with email '"+request.getEmail()+"' already exists.");
		}
		
		// Allow transferring user to another library
		if(!user.getLibrary().getId().equals(request.getLibraryId())){
			Library library = libraryService.findById(request.getLibraryId());
			user.setLibrary(library);
		}
		
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		
		return toResponse(userRepo.save(user));
	  }

	  // Delete user
	  @Transactional
	  public void deleteUser(Long id) {
		 User user = findById(id);
		 
		 if(!user.getBorrowedBooks().isEmpty()) {
			 throw new BadRequestException(
					 "Cannot delete user '" + user.getName()
	                    + "' — they still have " + user.getBorrowedBooks().size()
	                    + " book(s) borrowed. Please return them first.");
		 }
		  
		userRepo.deleteById(id);
	  }
	  
	  
	  //******************************************Internal helpers***************************************************
	  
	  
	  public User findById(Long id) {
	        return userRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "User not found with ID: " + id));
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

}
