package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.hcl.dto.AddProductToCartDto;
import com.hcl.entity.Cart;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICartDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.Impl.CartServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CartServiceImplTest {

	@Mock
	ICartDAO cartDAO;

	@Mock
	IProductDAO productDAO;

	@Mock
	IUserDAO userDAO;

	@InjectMocks
	CartServiceImpl cartServiceImpl;

	static AddProductToCartDto addProductToCartDto;
	static Cart cart;
	static Product product;
	static User user;
	static List<Cart> cartList;

	@BeforeAll
	public static void setUp() {
		addProductToCartDto = new AddProductToCartDto();
		addProductToCartDto.setUserId(1);
		addProductToCartDto.setQuantity(10);
		addProductToCartDto.setProductId(4);

		product = new Product();
		product.setProductId(4);

		user = new User();
		user.setUserId(1);

		cart = new Cart();
		cart.setCartId(3);
		cart.setProduct(product);
		cart.setQuantity(10);
		cart.setUser(user);
		cartList = new ArrayList<Cart>();
		cartList.add(cart);
	}

	@Test // (expected = NullPointerException.class)
	@Order(1)
	@DisplayName("Add products to cart: Positive Scenario")
	public void AddItemsToCart() throws UserDefinedException {

		when(productDAO.findById(4)).thenReturn(Optional.of(product));
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		// when(cartDAO.findByUserId(1)).thenReturn(null);
		// when(cartDAO.findByProductIdAndUserId(4, 1)).thenReturn(null);
		when(cartDAO.saveAndFlush(any(Cart.class))).thenAnswer(i -> {
			cart = i.getArgument(0);
			cart.setCartId(3);
			return cart;
		});

		String result = cartServiceImpl.addProdcuts(addProductToCartDto);
		verify(productDAO).findById(4);
		verify(userDAO).findById(1);
		verify(cartDAO).saveAndFlush(cart);
		// verify(cartDAO).findByUserId(1);
		// verify(cartDAO).findByProductIdAndUserId(4, 1);
		assertEquals("products added to cart", result);
	}

	@Test
	@Order(1)
	@DisplayName("Add products to cart: Negative Scenario1")
	public void AddItemsToCart1() throws UserDefinedException {

		when(productDAO.findById(4)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> cartServiceImpl.addProdcuts(addProductToCartDto));
	}

}
