package com.hcl.services;

import com.hcl.dto.AddProductToCartDto;
import com.hcl.entity.Cart;
import com.hcl.exception.UserDefinedException;

public interface ICartService {

	Cart addCart(Cart cart) throws UserDefinedException;

	String addProdcuts(AddProductToCartDto addProductToCartDto) throws UserDefinedException;

}
