package com.training.springboot.shoppingcart.service;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_SERVICE;

import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.entity.response.GetItemResponse;
import com.training.springboot.shoppingcart.error.ServiceNotAvailableException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ItemClientFallback implements ItemClient {

	// TODO: Implement the fallback behaviour for each of the services. HINT: only do this after completing ItemClient TODO.
}
