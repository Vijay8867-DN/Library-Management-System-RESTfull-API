package com.vijay.Library_Management_System.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vijay.Library_Management_System.dto.LibraryDto;
import com.vijay.Library_Management_System.dto.LibraryDto.Response;
import com.vijay.Library_Management_System.entity.Library;
import com.vijay.Library_Management_System.exception.DuplicateResourceException;
import com.vijay.Library_Management_System.exception.ResourceNotFoundException;
import com.vijay.Library_Management_System.repository.LibraryRepo;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService{

	private final LibraryRepo libraryRepo;
	
	  // Create
	@Transactional
	public LibraryDto.Response createLibrary(LibraryDto.Request request) {
		if(libraryRepo.existsByName(request.getName())) {
			throw new DuplicateResourceException("A library with the name '" + request.getName() + "' already exists.");
		}
		if(request.getEmail() != null && libraryRepo.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("A library with email '" + request.getEmail() + "' already exists.");
		}
		
		Library library = Library.builder()
							.name(request.getName())
							.address(request.getAddress())
							.phone(request.getPhone())
							.email(request.getEmail())
							.build();
		
		return toResponse(libraryRepo.save(library));
	}
	
	// Read All
	@Override
	@Transactional
	public List<LibraryDto.Response> getAllLibraries() {
		return libraryRepo.findAll()
				.stream()
				.map(this::toResponse)
				.collect(Collectors.toList());
	}
	
	// Read One
	@Override
	@Transactional
	public LibraryDto.Response getLibraryById(Long id) {
		return toResponse(findById(id));
	}

	//Update 
	@Override
	@Transactional
	public LibraryDto.Response updateLibrary(Long id, LibraryDto.Request request) {	
		Library library = findById(id);
		
		 // Only check uniqueness if the name actually changed
		if(!library.getName().equals(request.getName()) && libraryRepo.existsByName(request.getName())) {
			throw new DuplicateResourceException("A library with the name '" + request.getName() + "' already exists.");
		}
		
		library.setName(request.getName());
		library.setAddress(request.getAddress());
	    library.setPhone(request.getPhone());
	    library.setEmail(request.getEmail());
		
		return toResponse(libraryRepo.save(library));
	}

    //Delete
	@Override
	@Transactional
	public void deleteLibrary(Long id) {
		findById(id);
		libraryRepo.deleteById(id);
	}
	
	//Internal helper
	public Library findById(Long id) {
		return libraryRepo.findById(id).orElseThrow(()->
			new ResourceNotFoundException("A library with the Id '" + id + "' NOT FOUND."));
	}
	
	//Convert library object to Dto object(mapper)
	public LibraryDto.Response toResponse(Library library){
		return LibraryDto.Response.builder()
				.id(library.getId())
				.name(library.getName())
				.address(library.getAddress())
				.phone(library.getPhone())
				.email(library.getEmail())
				.totalBooks(library.getBooks() != null ? library.getBooks().size() : 0)
				.totalUsers(library.getUsers() != null ? library.getUsers().size(): 0)
				.createdAt(library.getCreatedAt())
				.build();
	}


}
