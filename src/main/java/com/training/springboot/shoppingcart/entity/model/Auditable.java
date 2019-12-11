package com.training.springboot.shoppingcart.entity.model;

import java.time.Instant;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public class Auditable {

	@CreatedDate
	private Instant createdAt;
	@LastModifiedDate
	private Instant modifiedAt;
	@CreatedBy
	private String createdBy;
	@LastModifiedBy
	private String lastModifiedBy;

}
