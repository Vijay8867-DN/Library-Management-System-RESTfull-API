package com.vijay.Library_Management_System.service;

import java.util.List;

import com.vijay.Library_Management_System.dto.BookDto;
import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.dto.UserDto.Response;
import com.vijay.Library_Management_System.entity.User;

import jakarta.validation.Valid;

public interface UserService {

	UserDto.Response registerUser( UserDto.Request request);

	List<UserDto.Response> getAllUsers();

	List<UserDto.Response> getUserByLibraryId(Long libraryId);

	UserDto.Response getUserById(Long userId);

	List<BookDto.Response> getBorrowedBooks(Long id);

	UserDto.Response updateUser(Long id, @Valid UserDto.Request request);

	void deleteUser(Long id);



}
