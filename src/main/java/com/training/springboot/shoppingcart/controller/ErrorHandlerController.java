package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.response.ErrorMessage;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

//TODO IMPLEMENT GLOBAL EXCEPTION HANDLER
public class ErrorHandlerController {


	private ResponseEntity<ErrorMessage> buildErrorMessageResponseEntity(Exception e, HttpStatus status) {
		return new ResponseEntity<>(
				ErrorMessage.builder().message(e.getMessage()).code(status.value()).build(),
				status);
	}

	//TODO HANDLE Exception
	public ResponseEntity<ErrorMessage> handleInternalError(Exception e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	//TODO HANDLE EntityNotFoundException
	public ResponseEntity<ErrorMessage> handleIBadRequest(EntityNotFoundException e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.NOT_FOUND);
	}

	//TODO HANDLE MethodArgumentNotValidException
	public ResponseEntity<ErrorMessage> handleIBadRequest(MethodArgumentNotValidException e) {
		return buildErrorMessageResponseEntity(e, HttpStatus.BAD_REQUEST);
	}

}