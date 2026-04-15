package com.vijay.Library_Management_System.service;

import java.util.List;

import com.vijay.Library_Management_System.dto.UserDto;
import com.vijay.Library_Management_System.dto.UserDto.Response;

import jakarta.validation.Valid;

public interface UserService {

	UserDto.Response registerUser( UserDto.Request request);

	List<UserDto.Response> getAllUsers();

}
