package com.vijay.Library_Management_System.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.entity.Book;

public interface BookRepo extends JpaRepository<Book,Long>{

}
