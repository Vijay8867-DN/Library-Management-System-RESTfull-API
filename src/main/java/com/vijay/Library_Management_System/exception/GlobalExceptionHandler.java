package com.vijay.Library_Management_System.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vijay.Library_Management_System.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	 // ── 400 Bad Request ───────────────────────────────────────────────────────
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.error(ex.getMessage()));
	}


    // ── 404 Not Found ─────────────────────────────────────────────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }
 
    // ── 409 Conflict ──────────────────────────────────────────────────────────
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }
 
    // ── 422 Unprocessable Entity ──────────────────────────────────────────────
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ApiResponse<Void>> handleBookUnavailable(BookNotAvailableException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponse.error(ex.getMessage()));
    }
 
    // ── 400 Validation Errors (from @Valid) ───────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {
 
    	Map<String, String> fieldErrors = new HashMap<>();
    	
    	ex.getBindingResult().getFieldErrors().forEach((error)->{
    		fieldErrors.put(error.getField(), error.getDefaultMessage());
		});
    	
    	ApiResponse<Map<String, String>> response = ApiResponse.<Map<String,String>>builder()
    			.success(false)
    			.message("Validation failed - check the 'data' field for details")
    			.data(fieldErrors)
    			.build();
    	
    	return ResponseEntity.badRequest().body(response);
//        Map<String, String> fieldErrors = new HashMap()<>();
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String field = ((FieldError) error).getField();
//            String msg   = error.getDefaultMessage();
//            fieldErrors.put(field, msg);
//        });
// 
//        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
//                .success(false)
//                .message("Validation failed — check the 'data' field for details")
//                .data(fieldErrors)
//                .build();
// 
//        return ResponseEntity.badRequest().body(response);
    }
 
    // ── 500 Fallback ──────────────────────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error: " + ex.getMessage()));
    }
}
