package com.trainingspringboot.shoppingcart.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

	private String traceId;
	private String operation;
	private String message;
	private int code;
}
