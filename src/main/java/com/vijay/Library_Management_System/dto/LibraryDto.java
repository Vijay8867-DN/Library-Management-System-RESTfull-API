package com.vijay.Library_Management_System.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LibraryDto {
    //this is for to receive the data sent from request
	
	@Getter	@Setter @NoArgsConstructor	@AllArgsConstructor @Builder
	public static class Request{
		
		@NotBlank(message = "Library name is required")
		private String name;
		
		@NotBlank(message = "Address is required")
		private String address;
		
		private String phone;
		
		@Email(message = "Invalid Email address")
		private String email;
	}
	
   //this is used to give the response
	public static class Response{
		   private Long id;
	        private String name;
	        private String address;
	        private String phone;
	        private String email;
	        private int totalBooks;
	        private int totalUsers;
	        private LocalDateTime createdAt;
	    }
}
