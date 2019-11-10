package com.trainingspringboot.shoppingcart.entity.request;

import com.trainingspringboot.shoppingcart.entity.model.CartItem;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartRequest {

	@NotEmpty
	private List<CartItem> items;

}
