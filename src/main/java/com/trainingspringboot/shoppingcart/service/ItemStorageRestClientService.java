package com.trainingspringboot.shoppingcart.service;

import static com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant.FRONT_SLASH_SEPARATOR;
import static com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_BASE_URL;
import static com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_DISPATCH_URI;

import com.trainingspringboot.shoppingcart.entity.request.DispatchItemRequest;
import com.trainingspringboot.shoppingcart.entity.response.GetItemResponse;
import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ItemStorageRestClientService {

	@Autowired
	private RestTemplate restTemplate;



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
