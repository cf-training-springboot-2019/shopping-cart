package com.training.springboot.shoppingcart.entity.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long cartUid;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items;
	private String state;

//TODO ADD Auditable fields included below
/*	private Instant createdAt;
	private Instant modifiedAt;
	private String createdBy;
	private String lastModifiedBy;
	*/
}
