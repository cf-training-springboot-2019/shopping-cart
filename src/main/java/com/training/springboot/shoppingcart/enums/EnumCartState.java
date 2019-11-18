package com.training.springboot.shoppingcart.enums;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum EnumCartState {
	PENDING, CANCELED, SUBMITTED;

	public static class Constants {

		public static final String REGEX = String
				.join("|", Arrays.stream(values()).map(e -> e.name()).collect(Collectors.toList()));
	}

}
