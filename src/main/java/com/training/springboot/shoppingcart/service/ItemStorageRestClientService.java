package com.training.springboot.shoppingcart.service;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.FRONT_SLASH_SEPARATOR;
import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_BASE_URL;
import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_DISPATCH_URI;

import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.entity.response.GetItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class ItemStorageRestClientService {

	private final RestTemplate restTemplate;


	public GetItemResponse getStoredItem(Long itemUid) {
		return restTemplate
				.getForEntity(String.join(FRONT_SLASH_SEPARATOR, ITEM_STORAGE_BASE_URL, String.valueOf(itemUid)),
						GetItemResponse.class).getBody();
	}

	public void dispatchItem(Long itemUid, DispatchItemRequest request) {
		restTemplate
				.postForEntity(String
								.join(FRONT_SLASH_SEPARATOR, ITEM_STORAGE_BASE_URL, String.valueOf(itemUid), ITEM_STORAGE_DISPATCH_URI),
						request, Object.class);
	}

}
