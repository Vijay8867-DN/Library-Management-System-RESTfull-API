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
import org.springframework.web.bind.annotation.RestController;

import com.vijay.Library_Management_System.dto.ApiResponse;
import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	// POST /api/users/register
    // Registers a user into a specific library (libraryId required in body)
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDto.Response>> register(
			@Valid @RequestBody UserDto.Request request){
		
		UserDto.Response created = userService.registerUser(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("User registered successfully",created));
	}
	
	 // GET /api/users
    // All users across all libraries
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDto.Response>>> getAll(){
		
		return ResponseEntity
				.ok(ApiResponse.success(userService.getAllUsers()));
	}
	
	// GET /api/users/library/{libraryId}
    // Users registered in a specific library
	@GetMapping("/library/{libraryId}")
	public ResponseEntity<ApiResponse<List<UserDto.Response>>> getByLibrary(
			@PathVariable Long libraryId){
		
		return ResponseEntity.ok(
				ApiResponse.success(userService.getUserByLibraryId(libraryId)));
	}
	
	// GET /api/users/{id}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto.Response>> getById(
			@PathVariable Long id){
		
		return ResponseEntity.ok(
				ApiResponse.success(userService.getUserById(id)));
	}
	
	// GET /api/users/{id}/books
    // All books currently borrowed by this user
	@GetMapping("/{id}/books")
	public ResponseEntity<ApiResponse<List<BookDto.Response>>> getBorrowedBooks(
			@PathVariable Long id){
		
		return ResponseEntity.ok(
				ApiResponse.success(userService.getBorrowedBooks(id)));
	}
	
	// PUT /api/users/{id}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto.Response>> update(
			 @PathVariable Long id,@Valid @RequestBody UserDto.Request request){
		
		return ResponseEntity.ok(
				ApiResponse.success("User updated successfully",userService.updateUser(id,request)));
	}
	

    // DELETE /api/users/{id}
    // Fails with 400 if user still has books borrowed
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(
			 @PathVariable Long id){
		
		userService.deleteUser(id);
		return ResponseEntity.ok(
				ApiResponse.success("User deleted successfully",null));
	}
}
