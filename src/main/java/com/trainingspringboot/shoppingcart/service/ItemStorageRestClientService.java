package com.trainingspringboot.shoppingcart.service;

import com.trainingspringboot.shoppingcart.entity.request.DispatchItemRequest;
import com.trainingspringboot.shoppingcart.entity.response.GetItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ItemStorageService {

	@Autowired
	private RestTemplate restTemplate;

	private final String ITEM_STORAGE_BASE_URL = "http://localhost:8080/item-storage/api/v1/items";
	private final String ITEM_STORAGE_DISPATCH_URI = "dispatch";
	private final String FRONT_SLASH_SEPARATOR = "/";

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
