package com.training.springboot.shoppingcart.controller;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.request.CreateCartItemRequest;
import com.training.springboot.shoppingcart.entity.request.CreateCartRequest;
import com.training.springboot.shoppingcart.entity.response.CreateCartResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartItemResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartResponse;
import com.training.springboot.shoppingcart.entity.response.UpdateCartRequest;
import com.training.springboot.shoppingcart.entity.response.UpdateCartResponse;
import com.training.springboot.shoppingcart.service.CartService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	/**
	 * @JavaDoc ModelMapper is a mapping tool easily configurable to accommodate most application defined entities check
	 * some configuration example at: http://modelmapper.org/user-manual/
	 */
	private final ModelMapper mapper;

	@PostMapping
	public ResponseEntity<CreateCartResponse> createCart(@RequestBody @Valid CreateCartRequest request) {
		return new ResponseEntity<>(mapper.map(cartService.save(mapper.map(request, Cart.class)), CreateCartResponse.class),
				HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<GetCartResponse>> getCart(@PathVariable("id") Long id) {
		Cart cart = cartService.get(id);
		GetCartResponse getCartResponse = mapper.map(cart, GetCartResponse.class);
		getCartResponse.setTotal(cartService.calculateCartTotal(cart));
		EntityModel<GetCartResponse> response = null;
		//TODO wrap GetCartResponse inside EntityModel response and add selfref link
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateCart(@PathVariable("id") Long id, @RequestBody @Valid UpdateCartRequest cart) {
		cart.setCartUid(id);
		return new ResponseEntity<>(mapper.map(cartService.update(mapper.map(cart, Cart.class)), UpdateCartResponse.class),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id) {
		cartService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<GetCartResponse>> listCarts() {
		return new ResponseEntity<>(
				cartService.list().stream().map(c -> {
							GetCartResponse response = mapper.map(c, GetCartResponse.class);
							response.setTotal(cartService.calculateCartTotal(c));
							return response;
						}
				)
						.collect(Collectors.toList()), HttpStatus.OK);
	}


	@GetMapping("/{id}/items")
	public ResponseEntity<List<GetCartItemResponse>> listCartItems(
			@PathVariable("id") Long cartUid) {
		List<GetCartItemResponse> cartItemResponseList = cartService.listCartItems(cartUid).stream()
				.map(i -> mapper.map(i, GetCartItemResponse.class)).collect(
						Collectors.toList());
		return new ResponseEntity<>(cartItemResponseList, HttpStatus.OK);
	}

	@GetMapping("/{cart-uid}/items/{item-uid}")
	public ResponseEntity<GetCartItemResponse> getCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long itemUid) {
		return new ResponseEntity<>(mapper.map(cartService.getCartItem(cartUid, itemUid), GetCartItemResponse.class),
				HttpStatus.OK);
	}

	@PutMapping("/{id}/items")
	public ResponseEntity<HttpStatus> addCartItem(@PathVariable("id") Long cartUid, @RequestBody CreateCartItemRequest createCartItemRequest) {
		//TODO ADD item and respective quantity within request to cart
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{cart-uid}/items/{item-uid}")
	public ResponseEntity<GetCartItemResponse> removeCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long cartItemUid) {
		//TODO Remove item from cart
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
