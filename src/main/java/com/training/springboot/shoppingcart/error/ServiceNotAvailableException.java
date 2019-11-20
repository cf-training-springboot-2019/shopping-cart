package com.training.springboot.shoppingcart.error;

import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;

public class ServiceNotAvailableException extends RuntimeException {


	public ServiceNotAvailableException(String service) {
		super(String.format(ShoppingCartConstant.SERVICE_NOT_AVAILABLE_MSG, service));
	}

}
