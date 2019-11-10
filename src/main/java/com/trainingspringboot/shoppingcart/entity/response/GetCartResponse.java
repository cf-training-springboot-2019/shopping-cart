package com.trainingspringboot.shoppingcart.entity.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse {

	private Long cartUid;
	private List<GetCartItemResponse> items;
	private String state;

}
