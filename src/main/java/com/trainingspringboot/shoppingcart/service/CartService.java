package com.trainingspringboot.shoppingcart.service;

import com.trainingspringboot.shoppingcart.entity.model.Cart;
import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import com.trainingspringboot.shoppingcart.entity.response.GetItemResponse;
import com.trainingspringboot.shoppingcart.enums.EnumCartState;
import com.trainingspringboot.shoppingcart.error.EntityNotFoundException;
import com.trainingspringboot.shoppingcart.repository.CartRepository;
import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService implements ICartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private RestTemplate restTemplate;


	@Override
	public Page<Cart> list(int page, int size) {
		return cartRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public List<Cart> list() {
		return cartRepository.findAll();
	}

	@Override
	public Cart get(Long id) throws EntityNotFoundException {
		return cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ShoppingCartConstant.CART, id));
	}

	@Override
	public void delete(Long id) {
		exists(id);
		cartRepository.deleteById(id);
	}

	@Override
	public Cart update(Cart entity) {
		Cart cart = get(entity.getCartUid());
		cart.setState(entity.getState());
		return cartRepository.save(cart);
	}

	@Override
	@Transactional
	public Cart save(Cart cart) {
		cart.setState(EnumCartState.PENDING.name());
		Map<Long, CartItem> cartItemMap = cart.getItems().stream()
				.collect(Collectors.toMap(CartItem::getItemUid, Function.identity(), (existing, replacement) -> {
					restTemplate
							.getForEntity("http://localhost:8080/item-storage/api/v1/items/" + existing.getItemUid(),
									GetItemResponse.class);
					existing.setQuantity(existing.getQuantity() + replacement.getQuantity());
					return existing;
				}));
		cart.setItems(cartItemMap.values().stream().collect(Collectors.toList()));
		final Cart persistedCart = cartRepository.save(cart);
		cart.getItems().stream().forEach(cartItem -> {
			cartItem.setCart(persistedCart);
			cartItemService.save(cartItem);
		});
		return cart;
	}

	@Override
	public List<CartItem> listCartItems(Long cartUid) {
		return cartItemService.listCartItems(cartUid);
	}

	@Override
	public BigDecimal calculateCartTotal(Cart cart) {
		return cart.getItems().stream().map(cartItem ->
				restTemplate
						.getForEntity("http://localhost:8080/item-storage/api/v1/items/" + cartItem.getItemUid(),
								GetItemResponse.class).getBody().getPriceTag().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
		).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public CartItem getCartItem(Long cartUid, Long cartItemUid) {
		exists(cartUid);
		return cartItemService.getCartCartItem(cartItemUid, cartUid);
	}

	private void exists(Long cartUid) {
		if (!cartRepository.existsById(cartUid)) {
			throw new EntityNotFoundException(ShoppingCartConstant.CART, cartUid);
		}
	}
}
