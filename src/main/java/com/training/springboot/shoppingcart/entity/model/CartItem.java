package com.training.springboot.shoppingcart.entity.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemUid;
	@ManyToOne
	private Cart cart;
	@NotNull
	@PositiveOrZero
	private Long itemUid;
	@NotNull
	@PositiveOrZero
	private Integer quantity;

	//TODO ADD Auditable fields included below
/*	private Instant createdAt;
	private Instant modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	*/

}
