package com.training.springboot.shoppingcart.repository;

import com.training.springboot.shoppingcart.entity.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
