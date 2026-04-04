package com.vijay.Library_Management_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijay.Library_Management_System.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
