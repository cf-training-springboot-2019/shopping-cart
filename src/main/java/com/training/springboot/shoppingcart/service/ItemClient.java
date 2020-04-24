package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.entity.response.GetItemResponse;
import com.training.springboot.shoppingcart.utils.annotation.ServiceOperation;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// TODO: Complete this interface using OpenFeign
public interface ItemClient {


}
