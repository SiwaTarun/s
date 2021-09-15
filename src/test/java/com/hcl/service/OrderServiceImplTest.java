package com.hcl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.hcl.dto.OrderHistoryResponseDto;
import com.hcl.dto.PlaceOrderDto;
import com.hcl.entity.Cart;
import com.hcl.entity.Orders;
import com.hcl.entity.Product;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICartDAO;
import com.hcl.repositry.IOrderDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.Impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class OrderServiceImplTest {
	@Mock
	ICartDAO cartDAO;

	@Mock
	IProductDAO productDAO;

	@Mock
	IUserDAO userDAO;

	@Mock
	IOrderDAO orderDAO;

	@InjectMocks
	OrderServiceImpl orderServiceImpl;

	static PlaceOrderDto placeOrderDto;
	static List<OrderHistoryResponseDto> orderHistoryList;
	static OrderHistoryResponseDto orderHistoryResponseDto;
	static List<Orders> orderList;
	static Orders orders;
	static User user;
	static List<Cart> cartList;
	static Cart cart;
	static Product product;

	@BeforeAll
	public static void setUp() {
		placeOrderDto = new PlaceOrderDto();
		placeOrderDto.setUserId(1);

		user = new User();
		user.setUserId(1);

		orders = new Orders();
		orders.setOrderId(9);
		LocalDate date = LocalDate.now();
		orders.setDate(date);
		orders.setTotalPrice(45000.0);
		orders.setUser(user);

		orderList = new ArrayList<Orders>();
		orderList.add(orders);

		orderHistoryResponseDto = new OrderHistoryResponseDto();
		orderHistoryResponseDto.setOrderId(9);
		orderHistoryResponseDto.setTotalPrice(45000.0);
		orderHistoryResponseDto.setUserId(1);
		orderHistoryResponseDto.setDate(date);

		orderHistoryList = new ArrayList<OrderHistoryResponseDto>();
		orderHistoryList.add(orderHistoryResponseDto);

		product = new Product();
		product.setProductId(4);

		cart = new Cart();
		cart.setCartId(3);
		cart.setProduct(product);
		cart.setQuantity(10);
		cart.setUser(user);

		cartList = new ArrayList<Cart>();
		cartList.add(cart);

	}

	@Test
	@Order(1)
	@DisplayName("Place Order: Positive Scenario")
	public void placeOrderTest() {
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		when(cartDAO.findByUserId(1)).thenReturn(cartList);
		when(orderDAO.saveAndFlush(any(Orders.class))).thenAnswer(i -> {
			orders = i.getArgument(0);
			orders.setOrderId(9);
			return orders;
		});
		doNothing().when(cartDAO).deleteByUser(user);

		String result = orderServiceImpl.placeOrder(placeOrderDto);
		verify(userDAO).findById(1);
		verify(cartDAO, times(2)).findByUserId(1);
		verify(orderDAO).saveAndFlush(orders);
		assertEquals("Order placed Successfully", result);
	}

	@Test
	@Order(2)
	@DisplayName("place order: Negative Scenario1")
	public void placeOrderTest1() throws UserDefinedException {

		when(userDAO.findById(1)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> orderServiceImpl.placeOrder(placeOrderDto));
	}

	@Test
	@Order(3)
	@DisplayName("get orderHistory: Positive Scenario")
	public void getOrderHistory() {
		when(userDAO.findById(1)).thenReturn(Optional.of(user));
		when(orderDAO.findByUserId(1)).thenReturn(orderList);
		List<OrderHistoryResponseDto> result = orderServiceImpl.getByUserId(1);
		verify(userDAO).findById(1);
		verify(orderDAO, times(2)).findByUserId(1);
		assertEquals(orderHistoryList, result);
	}

	@Test
	@Order(4)
	@DisplayName("get orderHistory : Negative Scenario1")
	public void getOrderHistory1() throws UserDefinedException {

		when(userDAO.findById(1)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> orderServiceImpl.getByUserId(1));
	}

};