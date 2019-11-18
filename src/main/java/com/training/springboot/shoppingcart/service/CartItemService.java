package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.model.CartItem;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import com.training.springboot.shoppingcart.repository.CartItemRepository;
import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CartItemService implements ICartItemService {


	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> listCartItems(Long cartUid) {
		return cartItemRepository.findByCartCartUid(cartUid);
	}

	@Override
	public void addItem(CartItem item) {

	}

	@Override
	public void removeItem(CartItem item) {

	}


	@Override
	public CartItem getCartCartItem(Long itemUid, Long cartUid) {
		return cartItemRepository.findByItemUidAndCartCartUid(itemUid, cartUid)
				.orElseThrow(() -> new EntityNotFoundException(
						ShoppingCartConstant.CART_ITEM, itemUid));
	}

	@Override
	public Page<CartItem> list(int page, int size) {
		return cartItemRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public List<CartItem> list() {
		return cartItemRepository.findAll();
	}

	@Override
	public CartItem get(Long id) {
		return cartItemRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(ShoppingCartConstant.CART_ITEM, id));
	}

	@Override
	public void delete(Long id) {

	}

	@Override
	public CartItem update(CartItem entity) {
		return null;
	}

	@Override
	public CartItem save(CartItem entity) {
		return cartItemRepository.save(entity);
	}

}
