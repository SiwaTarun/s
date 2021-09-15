package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.hcl.dto.ProductRequestDto;
import com.hcl.dto.ProductResponseDto;
import com.hcl.entity.Category;
import com.hcl.entity.Product;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICategoryDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.services.Impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceImplTest {

	@Mock
	IProductDAO productDAO;

	@Mock
	ICategoryDAO categoryDAO;

	@InjectMocks
	ProductServiceImpl productServiceImpl;

	static Product product;
	static Category category;
	static ProductRequestDto productRequestDto;
	static ProductResponseDto productResponseDto;
	static List<Product> allProductList;
	static List<ProductResponseDto> responseDtoList;

	@BeforeAll
	public static void setUp() {

		category = new Category();
		category.setCategoryId(3);
		category.setDescription("All wooden,plastic materials");
		category.setName("Furniture");

		product = new Product();
		product.setProductId(4);
		product.setProductName("Chair");
		product.setProductCategory("Wooden");
		product.setProductPrice(12000.0);
		product.setQuantity(1);
		product.setCategory(category);

		allProductList = new ArrayList<Product>();
		allProductList.add(product);

		productRequestDto = new ProductRequestDto();
		productRequestDto.setProductCategory("Wooden");
		productRequestDto.setProductName("Chair");

		productResponseDto = new ProductResponseDto();
		productResponseDto.setCategoryDescription("All wooden,plastic materials");
		productResponseDto.setCategoryName("Furniture");
		productResponseDto.setProductCategory("Wooden");
		productResponseDto.setProductName("Chair");
		productResponseDto.setProductPrice(12000.0);
		productResponseDto.setQuantity(1);

		responseDtoList = new ArrayList<ProductResponseDto>();
		responseDtoList.add(productResponseDto);

	}

	@Test
	@Order(1)
	@DisplayName("Product Adding: Positive Scenario")
	public void productAddingTest() {
		when(categoryDAO.findById(3)).thenReturn(Optional.of(category));
		when(productDAO.saveAndFlush(any(Product.class))).thenAnswer(i -> {
			product = i.getArgument(0);
			product.setProductId(4);
			return product;
		});

		Product result = productServiceImpl.addProduct(product);
		verify(categoryDAO).findById(3);
		verify(productDAO).saveAndFlush(product);
		assertEquals(product, result);
	}

	@Test
	@Order(3)
	@DisplayName("Product Adding: Negative Scenario")
	public void productAddingTest2() {
		when(categoryDAO.findById(3)).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> productServiceImpl.addProduct(product));

	}

	@Test
	@Order(4)
	@DisplayName("Product Search: Positive Scenario")
	public void productSearchTest() {
		when(productDAO.findByProductNameStartingWith("Chair")).thenReturn((allProductList));
//	when(productDAO.findByProductCategoryStartingWith("Wooden")).thenReturn((allProductList) );
		List<ProductResponseDto> result = productServiceImpl.getByProductName(productRequestDto);
		verify(productDAO, times(2)).findByProductNameStartingWith("Chair");
//		verify(productDAO,times(2)).findByProductCategoryStartingWith("Wooden");
		assertEquals(responseDtoList, result);
	}

	@Test
	@Order(5)
	@DisplayName("Product Search: Negative Scenario")
	public void productSearchTest2() {
		// when(productDAO.findByProductCategoryStartingWith("Wooden")).thenThrow(UserDefinedException.class);
		when(productDAO.findByProductNameStartingWith("Chair")).thenThrow(UserDefinedException.class);
		assertThrows(UserDefinedException.class, () -> productServiceImpl.getByProductName(productRequestDto));

	}

	@Test
	@Order(2)
	@DisplayName("All Product List: Positive Scenario")
	public void getAllproductsTest1() {
		when(productDAO.findAll()).thenReturn(allProductList);
		List<Product> result = productServiceImpl.displayAllProducts();
		verify(productDAO).findAll();
		assertEquals(allProductList, result);

	}

}
