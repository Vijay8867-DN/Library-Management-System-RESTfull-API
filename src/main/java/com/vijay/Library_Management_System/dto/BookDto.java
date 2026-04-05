package com.vijay.Library_Management_System.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookDto {

	 // ── Request ──────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor  @AllArgsConstructor @Builder
    public static class Request {
 
        @NotBlank(message = "Title is required")
        private String title;
 
        @NotBlank(message = "Author is required")
        private String author;
 
        @NotBlank(message = "ISBN is required")
        private String isbn;
 
        private String genre;
 
        @NotNull(message = "Library ID is required")
        private Long libraryId;
    }
 
    // ── Response ─────────────────────────────────────────────
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private String genre;
        private boolean available;
        private Long libraryId;
        private String libraryName;
        private Long borrowedByUserId;
        private String borrowedByUserName;
        private LocalDateTime createdAt;
    }
}
