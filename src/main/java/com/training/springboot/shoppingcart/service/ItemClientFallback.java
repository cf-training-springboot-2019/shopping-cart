package com.training.springboot.shoppingcart.service;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_SERVICE;

import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.entity.response.GetItemResponse;
import com.training.springboot.shoppingcart.error.ServiceNotAvailableException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ItemClientFallback implements ItemClient {

	@Override
	public ResponseEntity<GetItemResponse> getItem(Long id) {
		throw new ServiceNotAvailableException(ITEM_STORAGE_SERVICE);
	}

	@Override
	public ResponseEntity<List<GetItemResponse>> listItems() {
		throw new ServiceNotAvailableException(ITEM_STORAGE_SERVICE);
	}

	@Override
	public ResponseEntity<HttpStatus> dispatchItem(Long id, DispatchItemRequest request) {
		throw new ServiceNotAvailableException(ITEM_STORAGE_SERVICE);
	}
}
