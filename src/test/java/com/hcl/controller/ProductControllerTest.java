package com.hcl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.dto.AddProductRequestDto;
import com.hcl.dto.ProductRequestDto;
import com.hcl.dto.ProductResponseDto;
import com.hcl.entity.Category;
import com.hcl.entity.Product;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IProductService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductControllerTest {

	@Mock
	IProductService productService;

	@InjectMocks
	ProductController productController;

	static Product product;
	static AddProductRequestDto addproduct;
	static List<Product> productList;
	static ProductRequestDto requestDto;
	static List<ProductResponseDto> searchList;
	static ProductResponseDto productResponseDto;
	static Category category;

	@BeforeAll
	public static void setUp() {

		category = new Category();
		category.setCategoryId(2);
		category.setDescription("All wooden,plastic materials");
		category.setName("Furniture");

		product = new Product();
		product.setProductId(1);
		product.setProductName("Chair");
		product.setProductCategory("Wooden");
		product.setProductPrice(12000.0);
		product.setQuantity(1);
		product.setCategory(category);

		addproduct = new AddProductRequestDto();
		addproduct.setCategoryId(2);
		addproduct.setProductName("Chair");
		addproduct.setProductCategory("wooden materials");
		addproduct.setProductPrice(12000.0);
		addproduct.setQuantity(1);

		requestDto = new ProductRequestDto();
		requestDto.setProductCategory("wooden materials");
		requestDto.setProductName("Chair");

		productList = new ArrayList<Product>();
		productList.add(product);

		productResponseDto = new ProductResponseDto();
		productResponseDto.setCategoryDescription("All wooden,plastic materials");
		productResponseDto.setCategoryName("Furniture");
		productResponseDto.setProductName("Chair");
		productResponseDto.setProductCategory("wooden materials");
		productResponseDto.setProductPrice(12000.0);
		productResponseDto.setQuantity(1);

		searchList = new ArrayList<ProductResponseDto>();
		searchList.add(productResponseDto);

	}

	@Test
	@Order(1)
	@DisplayName("Product Addition:Positive Scenario")
	public void productRegistrationTest1() throws UserDefinedException {

		when(productService.addProduct(product)).thenReturn(product);
		ResponseEntity<?> result = productController.saveProduct(product);
		verify(productService).addProduct(product);
		assertEquals(product, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(1)
	@DisplayName("Product Addition:Negative Scenario")
	public void productRegistrationTest2() throws UserDefinedException {

		when(productService.addProduct(product)).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> productController.saveProduct(product));
	}

	@Test
	@Order(3)
	@DisplayName("All  Product details:Positive Scenario")
	public void getProductsDetailsTest1() {

		when(productService.displayAllProducts()).thenReturn(productList);
		List<Product> result = productController.displayProducts();
		verify(productService).displayAllProducts();
		assertEquals(productList, result);
	}

	@Test
	@Order(2)
	@DisplayName("Search product:positive scenario")
	public void getSearchProduct() throws UserDefinedException {
		when(productService.getByProductName(requestDto)).thenReturn(searchList);
		ResponseEntity<Object> result = productController.displayProductsByName(requestDto);
		verify(productService).getByProductName(requestDto);
		assertEquals(searchList, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName("Search product:Negative scenario")
	public void getSearchProduct2() throws UserDefinedException {
		when(productService.getByProductName(requestDto)).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> productController.displayProductsByName(requestDto));

	}
}
