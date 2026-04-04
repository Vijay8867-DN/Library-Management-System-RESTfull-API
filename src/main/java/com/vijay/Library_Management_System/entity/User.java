package com.vijay.Library_Management_System.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String phone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "library_id",nullable = false)
	private Library libray;
	
	@OneToMany(mappedBy = "borrowedBy")
	@Builder.Default
	private List<Book> borrowedBooks = new ArrayList<>();
	
	@Column(updatable = false)
	private LocalDateTime createdAt;
	 
	private LocalDateTime updatedAt;
	 
	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	        updatedAt = LocalDateTime.now();
	    }
	 
	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
}
