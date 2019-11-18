package com.training.springboot.shoppingcart.entity.response;


import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetItemResponse {

	private String name;
	private String state;
	private String description;
	private String market;
	private BigInteger stock;
	private BigDecimal priceTag;

}
