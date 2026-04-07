package com.vijay.Library_Management_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.dto.LibraryDto;
import com.vijay.Library_Management_System.dto.LibraryDto.Response;
import com.vijay.Library_Management_System.entity.Library;

import jakarta.validation.Valid;

public interface LibraryRepo extends JpaRepository<Library,Long>{

	boolean existsByName(String name);

	boolean existsByEmail(String email);


}
