package com.hcl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.AddProductRequestDto;
import com.hcl.dto.ProductRequestDto;
import com.hcl.dto.ProductResponseDto;
import com.hcl.entity.Product;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IProductService;

@RestController
@RequestMapping("products")
public class ProductController {
	@Autowired
	IProductService productService;

	@PostMapping
	public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) throws UserDefinedException {

		Product response = productService.addProduct(product);
		AddProductRequestDto addproduct = new AddProductRequestDto();
		BeanUtils.copyProperties(response, addproduct);
		addproduct.setCategoryId(response.getCategory().getCategoryId());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping
	public List<Product> displayProducts() {
		List<Product> list = new ArrayList<Product>();
		list = productService.displayAllProducts();
		return list;
	}

	@PostMapping("/productName")
	public ResponseEntity<Object> displayProductsByName( @RequestBody ProductRequestDto requestDto)
			throws UserDefinedException {

		List<ProductResponseDto> list;
		list = productService.getByProductName(requestDto);

		return new ResponseEntity<Object>(list, HttpStatus.OK);

	}
}
