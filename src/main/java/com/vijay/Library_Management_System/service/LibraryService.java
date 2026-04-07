package com.vijay.Library_Management_System.service;

import java.util.List;

import com.vijay.Library_Management_System.dto.LibraryDto;
import com.vijay.Library_Management_System.dto.LibraryDto.Response;

import jakarta.validation.Valid;

public interface LibraryService {

	LibraryDto.Response createLibrary(LibraryDto.Request request);

	List<LibraryDto.Response> getAllLibraries();

	LibraryDto.Response getLibraryById(Long id);

	LibraryDto.Response updateLibrary(Long id, LibraryDto.Request request);

	void deleteLibrary(Long id);

}
