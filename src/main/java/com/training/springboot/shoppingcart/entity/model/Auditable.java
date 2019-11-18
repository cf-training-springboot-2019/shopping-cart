package com.training.springboot.shoppingcart.entity.model;

import java.time.Instant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class Auditable {

	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private Instant modifiedAt;
	@CreatedDate
	private Instant createdAt;
	@LastModifiedBy
	private String lastModifiedBy;

}
