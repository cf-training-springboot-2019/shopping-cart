package com.trainingspringboot.shoppingcart.repository;

import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	List<CartItem> findByCartCartUid(Long cartUid);

	boolean existsByCartCartUidAndItemUid(Long cartItemUid, Long cartUid);

	Optional<CartItem> findByCartCartUidAndItemUid(Long cartItemUid, Long cartUid);

	Optional<CartItem> findByCartItemUidAndCartCartUid(Long cartItemUid, Long cartUid);
}
