package com.vijay.Library_Management_System.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private Boolean success;
	private String message;
	private T data;
	private LocalDateTime timeStamp;
	
	public static <T> ApiResponse<T> success(String message, T data){
		return ApiResponse.<T>builder()
				.success(true)
				.message(message)
				.data(data)
				.timeStamp(LocalDateTime.now())
				.build();
	}
	
	public static <T> ApiResponse<T> success(T data){
		return success("Success", data);
	}
	
	public static <T> ApiResponse<T> error(String message){
		return ApiResponse.<T>builder()
				.success(false)
				.message(message)
				.timeStamp(LocalDateTime.now())
				.build();
	}
	
}
