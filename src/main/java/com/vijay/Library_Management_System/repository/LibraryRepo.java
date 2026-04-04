package com.vijay.Library_Management_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.entity.Library;

public interface LibraryRepo extends JpaRepository<Library,Long>{

}
