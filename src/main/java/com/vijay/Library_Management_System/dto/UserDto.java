package com.vijay.Library_Management_System.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {

	// ── Request ──────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
 
        @NotBlank(message = "Name is required")
        private String name;
 
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email address")
        private String email;
 
        private String phone;
 
        @NotNull(message = "Library ID is required to register the user")
        private Long libraryId;
    }
 
    // ── Response ─────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private Long libraryId;
        private String libraryName;
        private List<BookDto.Response> borrowedBooks;
        private LocalDateTime createdAt;
    }
}
