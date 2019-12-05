package com.training.springboot.shoppingcart.entity.model;

import java.time.Instant;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long cartUid;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items;
	private String state;
	@CreatedDate
	private Instant createdAt;
	@LastModifiedDate
	private Instant modifiedAt;
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;


}
