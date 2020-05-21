package com.training.springboot.shoppingcart.entity.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartItemResponse {

	private Long cartItemUid;
	private Long itemUid;
	private Integer quantity;

}
