package com.hcl.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequestDto {

	@NotNull(message = "product_name cannot be null")
	private String productName;

	@NotNull(message = "product_category cannot be null")
	private String productCategory;

	@NotNull(message = "ProductPrice cannot be null")
	private double productPrice;

	@NotNull(message = "quantity cannot be null")
	private int quantity;

	@NotNull(message = "category cannot be null")
	private int CategoryId;

}
