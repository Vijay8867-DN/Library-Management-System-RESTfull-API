package com.vijay.Library_Management_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.dto.UserDto.Response;
import com.vijay.Library_Management_System.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);

	List<User> findByLibraryId(Long libraryId);

}
