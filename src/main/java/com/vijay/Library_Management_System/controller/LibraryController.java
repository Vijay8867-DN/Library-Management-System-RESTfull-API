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
import com.vijay.Library_Management_System.dto.LibraryDto;
import com.vijay.Library_Management_System.service.LibraryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/libraries")
public class LibraryController {

	private final LibraryService libraryService;
	
	 // POST /api/libraries
	@PostMapping
	public ResponseEntity<ApiResponse<LibraryDto.Response>> create(@Valid @RequestBody LibraryDto.Request request){
		LibraryDto.Response created = libraryService.createLibrary(request);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success("Library created Successfully",created));
	}
	
    // GET /api/libraries
	@GetMapping
	public ResponseEntity<ApiResponse<List<LibraryDto.Response>>> getAll(){
		
		return ResponseEntity.ok(ApiResponse.success(libraryService.getAllLibraries()));
	}
	
	 // GET /api/libraries/{id}
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<LibraryDto.Response>> getById(@PathVariable Long id){
		return ResponseEntity
				.ok(ApiResponse.success(libraryService.getLibraryById(id)));
	}
	
	// PUT /api/libraries/{id}
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<LibraryDto.Response>> update(@PathVariable Long id,@Valid 
				@RequestBody LibraryDto.Request request){
		return ResponseEntity.ok(ApiResponse.success("Library updated successfully"
				,libraryService.updateLibrary(id,request)));
	}
	// DELETE /api/libraries/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<LibraryDto.Response>> delete(@PathVariable Long id){
		libraryService.deleteLibrary(id);
		return ResponseEntity
				.ok(ApiResponse.success("Library deleted successfully",null));
	}
}
