package com.rishi.todoapp.exception;

import com.rishi.todoapp.dto.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<GenericResponse<String>> handleAll(Exception ex, WebRequest request){

		GenericResponse<String> errorResponse = GenericResponse.error(
				"Internal Server Error",
				ex.getMessage()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({UnExpectedException.class})
	public ResponseEntity<GenericResponse<String>> handleUnexpectedEvent(UnExpectedException ex){

		GenericResponse<String> errorResponse = GenericResponse.error(
				"Unexpected Event happened",
				ex.getMessage()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({NotFoundException.class})
	public ResponseEntity<GenericResponse<String>> handleNotFound(NotFoundException ex){

		GenericResponse<String> errorResponse = GenericResponse.error(
				"NOT FOUND",
				ex.getMessage()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({AlreadyExistException.class})
	public ResponseEntity<GenericResponse<String>> handleAlreadyExist(AlreadyExistException ex){

		GenericResponse<String> errorResponse = GenericResponse.error(
				"Already Exists",
				ex.getMessage()
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
}

