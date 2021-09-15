package com.hcl.services;

import java.util.List;

import com.hcl.dto.ProductRequestDto;
import com.hcl.dto.ProductResponseDto;
import com.hcl.entity.Product;
import com.hcl.exception.UserDefinedException;

public interface IProductService {

	Product addProduct(Product product) throws UserDefinedException;

	List<Product> displayAllProducts();

	List<ProductResponseDto> getByProductName(ProductRequestDto requestDto) throws UserDefinedException;
}
