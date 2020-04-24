package com.training.springboot.shoppingcart.entity.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartResponse extends RepresentationModel<GetCartResponse> {

	private Long cartUid;
	private List<GetCartItemResponse> items;
	private String state;
	private BigDecimal total;

}
