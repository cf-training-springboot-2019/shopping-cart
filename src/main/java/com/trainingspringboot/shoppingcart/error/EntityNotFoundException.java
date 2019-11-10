package com.trainingspringboot.shoppingcart.error;

import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String entity, Long id) {
		this(String.format(ShoppingCartConstant.ENTITY_NOT_FOUND_MSG, entity, id));
	}

	private EntityNotFoundException(String message) {
		super(message);
	}
}
