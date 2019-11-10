package com.trainingspringboot.shoppingcart.controller;

import com.trainingspringboot.shoppingcart.entity.response.ErrorMessage;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvice {


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleInternalError(Exception e) {
		return new ResponseEntity<>(
				ErrorMessage.builder().message(e.getMessage()).code(HttpStatus.I_AM_A_TEAPOT.value())
						.operation(MDC.get("operation")).build(), HttpStatus.I_AM_A_TEAPOT);
	}
}
