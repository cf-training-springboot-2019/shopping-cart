package com.trainingspringboot.shoppingcart.controller;

import com.trainingspringboot.shoppingcart.entity.model.Cart;
import com.trainingspringboot.shoppingcart.service.CartService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carts")
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * @JavaDoc ModelMapper is a mapping tool easily configurable to accommodate most application defined entities check
	 * some configuration example at: http://modelmapper.org/user-manual/
	 */
	//@Autowired
	//private ModelMapper mapper;
	@PostMapping
	public ResponseEntity<?> createCart(@RequestBody Cart request) {
		return new ResponseEntity<>(cartService.save(request), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getItem(@PathVariable("id") Long id) {
		Cart cart = cartService.get(id);
		return new ResponseEntity<>(cart, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateCart(@PathVariable("id") Long id, @RequestBody Cart cart) {
		cart.setCartUid(id);
		return new ResponseEntity<>(cartService.update(cart), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable("id") Long id) {
		cartService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<Cart>> listCarts() {
		return new ResponseEntity<>(cartService.list(), HttpStatus.OK);
	}

}
