package com.trainingspringboot.shoppingcart.repository;

import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
