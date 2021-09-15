package com.hcl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
	private String productName;
	private String productCategory;
	private double productPrice;
	private int quantity;
	private String categoryName;
	private String categoryDescription;

}
