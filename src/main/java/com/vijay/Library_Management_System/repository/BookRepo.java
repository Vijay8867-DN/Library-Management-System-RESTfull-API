package com.vijay.Library_Management_System.repository;


import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.dto.BookDto.Response;
import com.vijay.Library_Management_System.entity.Book;

public interface BookRepo extends JpaRepository<Book,Long>{

	boolean existsByIsbn(String isbn);

	List<Book> findByLibraryId(Long libraryId);

	List<Book> findByLibraryIdAndAuthorContainingIgnoreCase(Long libraryId, String author);

	Collection<Book> findByLibraryIdAndTitleContainingIgnoreCase(Long libraryId, String title);

	List<Book> findByLibraryIdAndAvailable(Long librayId, boolean b);

}
