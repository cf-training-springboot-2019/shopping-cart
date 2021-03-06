package com.training.springboot.shoppingcart.controller;

import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.FRONT_SLASH_SEPARATOR;
import static com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant.ITEM_STORAGE_BASE_URL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.request.CreateCartRequest;
import com.training.springboot.shoppingcart.entity.response.CreateCartResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartItemResponse;
import com.training.springboot.shoppingcart.entity.response.GetCartResponse;
import com.training.springboot.shoppingcart.entity.response.UpdateCartRequest;
import com.training.springboot.shoppingcart.entity.response.UpdateCartResponse;
import com.training.springboot.shoppingcart.service.CartService;
import com.training.springboot.shoppingcart.utils.annotation.ServiceOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CartController {

	@Autowired
	private CartService cartService;

	/**
	 * @JavaDoc ModelMapper is a mapping tool easily configurable to accommodate most application defined entities check
	 * some configuration example at: http://modelmapper.org/user-manual/
	 */
	@Autowired
	private ModelMapper mapper;

	@PostMapping
	@ServiceOperation("createCart")
	public ResponseEntity<CreateCartResponse> createCart(@RequestBody @Valid CreateCartRequest request) {
		return new ResponseEntity<>(mapper.map(cartService.save(mapper.map(request, Cart.class)), CreateCartResponse.class),
				HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@ServiceOperation("getCart")
	public ResponseEntity<GetCartResponse> getCart(@PathVariable("id") Long id) {
		Cart cart = cartService.get(id);
		GetCartResponse response = mapper.map(cart, GetCartResponse.class);
		response.setTotal(cartService.calculateCartTotal(cart));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	@ServiceOperation("updateCart")
	public ResponseEntity<?> updateCart(@PathVariable("id") Long id, @RequestBody @Valid UpdateCartRequest cart) {
		cart.setCartUid(id);
		return new ResponseEntity<>(mapper.map(cartService.update(mapper.map(cart, Cart.class)), UpdateCartResponse.class),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ServiceOperation("deleteCart")
	public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") Long id) {
		cartService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	@ServiceOperation("listCarts")
	public ResponseEntity<Page<GetCartResponse>> listCarts(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		return new ResponseEntity<>(new PageImpl<>(
				cartService.list(page, size).stream().map(c -> {
							GetCartResponse response = mapper.map(c, GetCartResponse.class);
							response.setTotal(cartService.calculateCartTotal(c));
							return response;
						}
				)
						.collect(Collectors.toList())), HttpStatus.OK);
	}

	@GetMapping("/{id}/items")
	@ServiceOperation("listCartItems")
	public ResponseEntity<CollectionModel<EntityModel<GetCartItemResponse>>> listCartItems(
			@PathVariable("id") Long cartUid) {
		List<EntityModel<GetCartItemResponse>> entityModels = cartService.listCartItems(cartUid).stream().map(c ->
				new EntityModel<>(mapper.map(c, GetCartItemResponse.class),
						linkTo(methodOn(CartController.class).getCartItem(cartUid, c.getItemUid())).withRel("Get item"),
						linkTo(methodOn(CartController.class).deleteItem(c.getItemUid())).withRel("Decrease item qty"))
		).collect(Collectors.toList());
		return new ResponseEntity<>(new CollectionModel<>(entityModels), HttpStatus.OK);
	}

	@GetMapping("/{cart-uid}/items/{item-uid}")
	@ServiceOperation("getCartItem")
	public ResponseEntity<EntityModel<GetCartItemResponse>> getCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long itemUid) {
		GetCartItemResponse item = mapper.map(cartService.getCartItem(cartUid, itemUid), GetCartItemResponse.class);
		EntityModel model = new EntityModel<>(item,
				new Link(String.join(FRONT_SLASH_SEPARATOR, ITEM_STORAGE_BASE_URL, String.valueOf(itemUid)))
						.withRel("details"));
		return new ResponseEntity<>(model,
				HttpStatus.OK);
	}

	@PutMapping("/{id}/items")
	@ServiceOperation("addCartItem")
	public ResponseEntity<HttpStatus> addCartItem(@PathVariable("id") Long cartUid) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{cart-uid}/items/{item-uid}")
	@ServiceOperation("removeCartItem")
	public ResponseEntity<GetCartItemResponse> removeCartItem(@PathVariable("cart-uid") Long cartUid,
			@PathVariable("item-uid") Long cartItemUid) {
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
