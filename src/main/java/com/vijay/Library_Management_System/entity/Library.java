package com.vijay.Library_Management_System.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "libraries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Library {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	private String phone;
	private String email;
	
	@OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@Builder.Default
	private List<Book> books = new ArrayList<>();
	
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
	@PrePersist
	protected void onCreated() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
