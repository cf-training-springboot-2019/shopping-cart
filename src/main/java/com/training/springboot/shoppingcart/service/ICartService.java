package com.training.springboot.shoppingcart.service;

import com.training.springboot.shoppingcart.entity.model.Cart;
import com.training.springboot.shoppingcart.entity.model.CartItem;
import java.math.BigDecimal;
import java.util.List;

public interface ICartService extends ICrudService<Cart> {

	List<CartItem> listCartItems(Long cartUid);

	BigDecimal calculateCartTotal(Cart cart);

}
