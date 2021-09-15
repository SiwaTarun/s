package com.hcl.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dto.AddProductToCartDto;
import com.hcl.entity.Cart;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICartDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.ICartService;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	private ICartDAO cartDAO;

	@Autowired
	private IProductDAO productDAO;

	@Autowired
	private IUserDAO userDAO;

	@Override
	public String addProdcuts(AddProductToCartDto addProductToCartDto) throws UserDefinedException {
		if (productDAO.findById(addProductToCartDto.getProductId()).isPresent()
				&& userDAO.findById(addProductToCartDto.getUserId()).isPresent()
				&& addProductToCartDto.getQuantity() > 0) {
			Cart cart = new Cart();
			Product p = new Product();
			User u = new User();
			u.setUserId(addProductToCartDto.getUserId());
			p.setProductId(addProductToCartDto.getProductId());
			cart.setProduct(p);
			cart.setUser(u);
			cart.setQuantity(addProductToCartDto.getQuantity());
			if (cartDAO.findByUserId(addProductToCartDto.getUserId()).isEmpty()) {
				addCart(cart);
			}

			else if (cartDAO
					.findByProductIdAndUserId(addProductToCartDto.getProductId(), addProductToCartDto.getUserId())
					.isEmpty()) {
				addCart(cart);
			} else {
				throw new UserDefinedException("Items exists already");
			}
		}

		else {
			throw new UserDefinedException("Products not added successfully");
		}

		return "products added to cart";
	}

	@Override
	public Cart addCart(Cart cart) throws UserDefinedException {

		return cartDAO.saveAndFlush(cart);
	}

}
