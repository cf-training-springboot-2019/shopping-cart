package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.model.CartItem;
import com.training.springboot.shoppingcart.entity.request.DispatchItemRequest;
import com.training.springboot.shoppingcart.enums.EnumCartState;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import com.training.springboot.shoppingcart.repository.CartRepository;
import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

	private final CartRepository cartRepository;

	private final CartItemService cartItemService;

	private final ItemStorageRestClientService itemClient;

	@Override
	public Page<Cart> list(int page, int size, String field, String order) {
		return null;
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
		if (entity.getState().equalsIgnoreCase(EnumCartState.SUBMITTED.name()) && !cart.getState()
				.equalsIgnoreCase(EnumCartState.SUBMITTED.name())) {
			cart = get(entity.getCartUid());
			cart.getItems().forEach(i -> itemClient
					.dispatchItem(i.getItemUid(), DispatchItemRequest.builder().quantity(i.getQuantity()).build()));
		}
		if (!cart.getState().equalsIgnoreCase(EnumCartState.SUBMITTED.name())) {
			cart.setState(entity.getState());
			cartRepository.save(cart);
		}
		return cart;
	}

	//TODO Convert method to become transactional denying cart/cartItem persistence if item in cart item list does not exist.
	@Override
	public Cart save(Cart cart) {
		cart.setState(EnumCartState.PENDING.name());
		Map<Long, CartItem> cartItemMap = cart.getItems().stream()
				.collect(Collectors.toMap(CartItem::getItemUid, Function.identity(), (existing, replacement) -> {
					existing.setQuantity(existing.getQuantity() + replacement.getQuantity());
					return existing;
				}));
		cart.setItems(cartItemMap.values().stream().collect(Collectors.toList()));
		final Cart persistedCart = cartRepository.save(cart);
		cart.getItems().stream().forEach(cartItem -> {
			itemClient.getItem(cartItem.getItemUid());
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
				itemClient.getItem(cartItem.getItemUid())
						.getPriceTag().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
		).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public CartItem getCartItem(Long cartUid, Long itemUid) {
		exists(cartUid);
		return cartItemService.getCartCartItem(itemUid, cartUid);
	}

	private void exists(Long cartUid) {
		if (!cartRepository.existsById(cartUid)) {
			throw new EntityNotFoundException(ShoppingCartConstant.CART, cartUid);
		}
	}
}
