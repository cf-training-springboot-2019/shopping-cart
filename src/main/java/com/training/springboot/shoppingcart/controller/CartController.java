package com.training.springboot.shoppingcart.controller;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_BASE_URL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.model.CartItem;
import com.training.springboot.shoppingcart.entity.request.CreateCartItemRequest;
import com.training.springboot.shoppingcart.entity.request.CreateCartRequest;
import com.training.springboot.shoppingcart.entity.response.CreateCartResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartItemResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartResponse;
import com.training.springboot.shoppingcart.entity.response.UpdateCartRequest;
import com.training.springboot.shoppingcart.entity.response.UpdateCartResponse;
import com.training.springboot.shoppingcart.service.CartItemService;
import com.training.springboot.shoppingcart.service.CartService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final CartItemService cartItemService;

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
		GetCartResponse cartResponse = mapper.map(cart, GetCartResponse.class);
		cartResponse.setTotal(cartService.calculateCartTotal(cart));
		EntityModel<GetCartResponse> response = new EntityModel<>(cartResponse,
				linkTo(methodOn(CartController.class).getCart(id)).withSelfRel()
		);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> updateCart(@PathVariable("id") Long id, @RequestBody @Valid UpdateCartRequest cart) {
		cart.setCartUid(id);
		return new ResponseEntity<>(mapper.map(cartService.update(mapper.map(cart, Cart.class)), UpdateCartResponse.class),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCart(@PathVariable("id") Long id) {
		cartService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<Page<GetCartResponse>> listCarts(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		Page<Cart> cartPage = cartService.list(page, size);
		return new ResponseEntity<>(new PageImpl<>(
				cartService.list(page, size).stream().map(c -> {
							GetCartResponse response = mapper.map(c, GetCartResponse.class);
							response.setTotal(cartService.calculateCartTotal(c));
							return response;
						}
				)
						.collect(Collectors.toList()), cartPage.getPageable(), cartPage.getTotalElements()), HttpStatus.OK);
	}

	// Cart Item controller methods

	@GetMapping("/{cart-uid}/items")
	public ResponseEntity<CollectionModel<EntityModel<GetCartItemResponse>>> listCartItems(
			@PathVariable("cart-uid") Long cartUid) {
		List<EntityModel<GetCartItemResponse>> entityModels = cartService.listCartItems(cartUid).stream().map(c ->
				new EntityModel<>(mapper.map(c, GetCartItemResponse.class),
						linkTo(methodOn(CartController.class).getCartItem(cartUid, c.getItemUid()))
								.withRel("Get item"),
						linkTo(methodOn(CartController.class).removeCartItem(c.getCart().getCartUid(), c.getItemUid()))
								.withRel("Decrease item qty"),
						new Link(String.join("/", Arrays.asList(ITEM_STORAGE_BASE_URL, String.valueOf(c.getItemUid()))), "details"))
		).collect(Collectors.toList());
		return new ResponseEntity<>(new CollectionModel<>(entityModels), HttpStatus.OK);
	}

	@GetMapping("/{cart-uid}/items/{item-uid}")
	public ResponseEntity<GetCartItemResponse> getCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long itemUid) {
		return new ResponseEntity<>(mapper.map(cartService.getCartItem(cartUid, itemUid), GetCartItemResponse.class),
				HttpStatus.OK);
	}

	@PutMapping("/{cart-uid}/items")
	public ResponseEntity<HttpStatus> addCartItem(@PathVariable("cart-uid") Long cartUid,
			@RequestBody CreateCartItemRequest createCartItemRequest) {
		Long itemUid = createCartItemRequest.getItemUid();
		Integer quantity = createCartItemRequest.getQuantity();

		Cart cartWithItem = cartService.get(cartUid);
		CartItem cartItemToUpdate = cartWithItem.getItems().stream()
				.filter(cartItem -> cartItem.getItemUid().equals(itemUid))
				.findFirst()
				.orElse(null);

		if (cartItemToUpdate == null) {
			CartItem newCartItem = new CartItem();
			newCartItem.setItemUid(itemUid);
			newCartItem.setQuantity(quantity);
			newCartItem.setCart(cartWithItem);
			cartWithItem.getItems().add(cartItemService.save(newCartItem));
		} else {
			cartItemToUpdate.setItemUid(itemUid);
			cartItemToUpdate.setQuantity(quantity);
		}

		cartService.save(cartWithItem);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{cart-uid}/items/{item-uid}")
	public ResponseEntity<GetCartItemResponse> removeCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long cartItemUid) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
