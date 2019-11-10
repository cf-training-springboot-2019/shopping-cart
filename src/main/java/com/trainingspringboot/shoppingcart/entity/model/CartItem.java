package com.trainingspringboot.shoppingcart.entity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem extends Auditable {

	@Id
	@GeneratedValue
	private Long cartItemUid;

	private Long itemUid;

	private Integer quantity;


}
