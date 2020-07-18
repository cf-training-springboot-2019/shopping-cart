package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.response.ErrorMessage;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
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

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConflict(ConstraintViolationException e) {
		String message = e.getConstraintViolations()
				.stream()
				.map(c -> String.join(" ", Arrays.asList(c.getPropertyPath().toString(), c.getMessage())))
				.collect(Collectors.joining(". "));
		return buildErrorMessageResponseEntity(new RuntimeException(message), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleIBadRequest(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getFieldErrors()
				.stream()
				.map(f -> String.join(" ", Arrays.asList(f.getField(), f.getDefaultMessage())))
				.sorted()
				.collect(Collectors.joining(". "));
		return buildErrorMessageResponseEntity(new RuntimeException(message), HttpStatus.BAD_REQUEST);
	}

}
