package com.trainingspringboot.shoppingcart.service;

import com.trainingspringboot.shoppingcart.entity.model.Cart;
import com.trainingspringboot.shoppingcart.repository.CartItemRepository;
import com.trainingspringboot.shoppingcart.repository.CartRepository;
import java.util.List;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Page<Cart> list(int size, int page) {
		return null;
	}

	@Override
	public List<Cart> list() {
		return cartRepository.findAll();
	}

	@Override
	public Cart get(Long id) {
		return null;
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public Cart update(Cart entity) {
		return null;
	}

	@Override
	@Transactional
	public Cart save(Cart cart) {
		MDC.put("operation", "createCart");
		cart.getItems().stream().forEach(cartItem -> {
					restTemplate
							.getForEntity("http://localhost:8080/item-storage/api/v1/items/" + cartItem.getItemUid(), Object.class);
					//cartItemRepository.save(cartItem);
				}
		);
		cart.setState("PENDING");
		return cartRepository.save(cart);
	}
}
