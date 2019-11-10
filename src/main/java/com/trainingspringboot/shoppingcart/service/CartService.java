package com.trainingspringboot.shoppingcart.service;

import com.trainingspringboot.shoppingcart.entity.model.Cart;
import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import com.trainingspringboot.shoppingcart.entity.response.GetCartResponse;
import com.trainingspringboot.shoppingcart.entity.response.GetItemResponse;
import com.trainingspringboot.shoppingcart.enums.EnumCartState;
import com.trainingspringboot.shoppingcart.error.EntityNotFoundException;
import com.trainingspringboot.shoppingcart.repository.CartItemRepository;
import com.trainingspringboot.shoppingcart.repository.CartRepository;
import com.trainingspringboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.math.BigDecimal;
import java.util.List;
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
	private CartItemRepository cartItemRepository;

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
		cartRepository.delete(get(id));
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
		cart.getItems().stream().forEach(cartItem -> {
					restTemplate
							.getForEntity("http://localhost:8080/item-storage/api/v1/items/" + cartItem.getItemUid(), Object.class);
				}
		);
		return cartRepository.save(cart);
	}

	@Override
	public List<CartItem> listCartItems(Long cartUid) {
		return null;
	}

	@Override
	public Page<CartItem> listCartItems(Long cartUid, int page, int size) {
		return null;
	}

	@Override
	public BigDecimal calculateCost(Cart cart) {
		return cart.getItems().stream().map(cartItem ->
				restTemplate
						.getForEntity("http://localhost:8080/item-storage/api/v1/items/" + cartItem.getItemUid(),
								GetItemResponse.class).getBody().getPriceTag().multiply(BigDecimal.valueOf(cartItem.getQuantity()))
		).collect(Collectors.toList()).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
