package com.hcl.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductToCartDto {

	@NotNull(message = "UserId cannot be Null")
	private int userId;
	@NotNull(message = "quantity cannot be Null")
	private int quantity;
	@NotNull(message = "product_id cannot be Null")
	private int productId;

}
