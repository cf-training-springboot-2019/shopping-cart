package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.entity.response.GetItemResponse;
import com.training.springboot.shoppingcart.utils.annotation.ServiceOperation;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "item-storage")
public interface ItemClient {

	@GetMapping("/item-storage/api/v1/items/{id}")
	@ServiceOperation("getItem")
	ResponseEntity<GetItemResponse> getItem(@PathVariable("id") Long id);

	@GetMapping("/item-storage/api/v1/items")
	@ServiceOperation("listItems")
	ResponseEntity<List<GetItemResponse>> listItems();

	@PostMapping("/item-storage/api/v1/items/{id}/dispatch")
	@ServiceOperation("dispatchItem")
	ResponseEntity<HttpStatus> dispatchItem(@PathVariable("id") Long id,
			@RequestBody DispatchItemRequest request);

}
