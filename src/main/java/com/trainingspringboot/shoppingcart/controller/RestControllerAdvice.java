package com.trainingspringboot.shoppingcart.controller;

import com.trainingspringboot.shoppingcart.entity.response.ErrorMessage;
import com.trainingspringboot.shoppingcart.error.EntityNotFoundException;
import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleNotFoundError(Exception e) {
		return buildErrorMessageResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
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
