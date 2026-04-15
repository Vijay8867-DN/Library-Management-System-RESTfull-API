package com.vijay.Library_Management_System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vijay.Library_Management_System.dto.ApiResponse;
import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDto.Response>> register(
			@Valid @RequestBody UserDto.Request request){
		
		UserDto.Response created = userService.registerUser(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("User registered successfully",created));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserDto.Response>>> getAll(){
		return ResponseEntity
				.ok(ApiResponse.success(userService.getAllUsers()));
	}
}
