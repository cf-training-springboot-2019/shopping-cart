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

	private String operation;
	private int code;
	private String message;
}
