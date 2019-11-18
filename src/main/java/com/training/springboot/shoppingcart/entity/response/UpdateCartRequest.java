package com.training.springboot.shoppingcart.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartRequest {

	@JsonIgnore
	private Long cartUid;
	@Pattern(regexp = "PENDING|CANCELED|SUBMITTED")
	private String state;

}
