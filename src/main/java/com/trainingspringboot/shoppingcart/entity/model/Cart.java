package com.trainingspringboot.shoppingcart.entity.model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cart extends Auditable {

	@Id
	@GeneratedValue
	private Long cartUid;

	@OneToMany
	private List<CartItem> items;

	private String state;

}
