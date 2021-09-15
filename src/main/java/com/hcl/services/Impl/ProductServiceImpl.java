package com.hcl.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dto.ProductRequestDto;
import com.hcl.dto.ProductResponseDto;
import com.hcl.entity.Product;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICategoryDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductDAO productDAO;

	@Autowired
	private ICategoryDAO categoryDAO;

	@Override
	public Product addProduct(Product product) throws UserDefinedException {
		if (categoryDAO.findById(product.getCategory().getCategoryId()).isPresent()) {
			return productDAO.saveAndFlush(product);
		}
		throw new UserDefinedException("Category Not found....");
	}

	@Override
	public List<Product> displayAllProducts() {
		return productDAO.findAll();
	}

	@Override
	public List<ProductResponseDto> getByProductName(ProductRequestDto requestDto) throws UserDefinedException {
		List<ProductResponseDto> list = new ArrayList<>();
		List<Product> response = new ArrayList<Product>();
		if (!productDAO.findByProductNameStartingWith(requestDto.getProductName()).isEmpty()) {
			response = productDAO.findByProductNameStartingWith(requestDto.getProductName());
		} else if (!productDAO.findByProductCategoryStartingWith(requestDto.getProductCategory()).isEmpty()) {
			response = productDAO.findByProductCategoryStartingWith(requestDto.getProductCategory());
		} else {
			throw new UserDefinedException("Product not found");
		}
		response.stream().forEach(c -> {
			ProductResponseDto responseDto = new ProductResponseDto();
			responseDto.setProductName(c.getProductName());
			responseDto.setProductCategory(c.getProductCategory());
			responseDto.setQuantity(c.getQuantity());
			responseDto.setProductPrice(c.getProductPrice());
			responseDto.setCategoryName(c.getCategory().getName());
			responseDto.setCategoryDescription(c.getCategory().getDescription());
			list.add(responseDto);

		});
		return list;

	}

}
