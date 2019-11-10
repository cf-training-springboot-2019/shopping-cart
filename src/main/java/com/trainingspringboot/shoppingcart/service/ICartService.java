package com.trainingspringboot.shoppingcart.service;

import com.trainingspringboot.shoppingcart.entity.model.Cart;
import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;

public interface ICartService extends ICrudService<Cart> {

	List<CartItem> listCartItems(Long cartUid);
	Page<CartItem> listCartItems(Long cartUid, int page, int size);
	BigDecimal calculateCost(Cart cart);

}
