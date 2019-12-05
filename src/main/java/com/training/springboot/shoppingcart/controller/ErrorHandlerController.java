package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.response.ErrorMessage;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleInternalError(Exception e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorMessage> buildErrorMessageResponseEntity(Exception e, HttpStatus status) {
		return new ResponseEntity<>(
				ErrorMessage.builder().message(e.getMessage()).code(status.value()).build(),
				status);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleIBadRequest(EntityNotFoundException e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.NOT_FOUND);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleIBadRequest(MethodArgumentNotValidException e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.BAD_REQUEST);
	}

}
