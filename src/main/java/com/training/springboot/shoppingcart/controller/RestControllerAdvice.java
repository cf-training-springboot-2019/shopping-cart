package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.response.ErrorMessage;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleNotFoundError(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleBadRequest(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleInternalError(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorMessage> buildErrorMessageResponseEntity(String msg, HttpStatus httpStatus) {
		log.error(msg);
		return new ResponseEntity<>(
				ErrorMessage.builder()
						.message(msg)
						.code(httpStatus.value())
						.traceId(MDC.get(ShoppingCartConstant.TRACE_ID))
						.operation(MDC.get(ShoppingCartConstant.OPERATION))
						.build(),
				httpStatus);
	}

}
