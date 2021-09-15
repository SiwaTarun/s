package com.hcl.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcl.dto.AddProductToCartDto;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.ICartService;

@RestController
@RequestMapping("carts")
public class CartController {
	
	@Autowired
	private ICartService cartService;

	@PostMapping
	public ResponseEntity<String> saveProductsToCart(@Valid @RequestBody AddProductToCartDto addProductToCartDto) throws UserDefinedException {
		
		return new ResponseEntity<String>(cartService.addProdcuts(addProductToCartDto), HttpStatus.OK);
	}


}
