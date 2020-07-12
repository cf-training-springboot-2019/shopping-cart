package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.model.CartItem;
import com.training.springboot.shoppingcart.error.EntityNotFoundException;
import com.training.springboot.shoppingcart.repository.CartItemRepository;
import com.training.springboot.shoppingcart.utils.constant.ShoppingCartConstant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {


	private final CartItemRepository cartItemRepository;

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
	public Page<CartItem> list(int page, int size, String field, String order) {
		//TODO return Paged CartItem based on PageRequest
		return null;
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
