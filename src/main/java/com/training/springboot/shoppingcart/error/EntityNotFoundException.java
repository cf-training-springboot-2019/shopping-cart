package com.training.springboot.shoppingcart.error;

import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;

public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String entity, Long id) {
		this(String.format(ShoppingCartConstant.ENTITY_NOT_FOUND_MSG, entity, id));
	}

	private EntityNotFoundException(String message) {
		super(message);
	}
}
