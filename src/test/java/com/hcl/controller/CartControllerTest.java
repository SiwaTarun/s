package com.hcl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.hcl.dto.AddProductToCartDto;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.ICartService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CartControllerTest {

	@Mock
	ICartService cartService;

	@InjectMocks
	CartController cartController;

	static AddProductToCartDto addProductToCartDto;

	@BeforeAll
	public static void setUp() {
		addProductToCartDto = new AddProductToCartDto();
		addProductToCartDto.setQuantity(1);
		addProductToCartDto.setProductId(4);
		addProductToCartDto.setQuantity(10);

	}

	@Test
	@Order(1)
	@DisplayName("Add products to cart:Positive Scenario")
	public void AddProdcutsToCart() throws UserDefinedException {

		when(cartService.addProdcuts(addProductToCartDto)).thenReturn("Products added into cart");

		ResponseEntity<String> result = cartController.saveProductsToCart(addProductToCartDto);

		verify(cartService).addProdcuts(addProductToCartDto);
		assertEquals("Products added into cart", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName("Add products to cart: Negative Scenario")
	public void AddProdcutsToCart1() throws UserDefinedException {

		when(cartService.addProdcuts(addProductToCartDto)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> cartController.saveProductsToCart(addProductToCartDto));

	}
}
