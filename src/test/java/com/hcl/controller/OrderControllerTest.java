package com.hcl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import com.hcl.dto.OrderHistoryResponseDto;
import com.hcl.dto.PlaceOrderDto;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IOrderService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class OrderControllerTest {
	@Mock
	IOrderService orderServices;

	@InjectMocks
	OrderController orderController;

	static PlaceOrderDto placeOrderDto;
	static List<OrderHistoryResponseDto> orderHistoryList;
	static OrderHistoryResponseDto orderHistoryResponseDto;

	@BeforeAll
	public static void setUp() {
		placeOrderDto = new PlaceOrderDto();
		placeOrderDto.setUserId(1);
		orderHistoryList = new ArrayList<OrderHistoryResponseDto>();
		orderHistoryResponseDto = new OrderHistoryResponseDto();
		orderHistoryResponseDto.setUserId(1);
		orderHistoryResponseDto.setOrderId(6);
		orderHistoryResponseDto.setTotalPrice(23000.0);
		LocalDate date = LocalDate.now();
		orderHistoryResponseDto.setDate(date);
		orderHistoryList.add(orderHistoryResponseDto);
	}

	@Test
	@Order(1)
	@DisplayName("Place Order:Positive Scenario")
	public void placeOrder() throws UserDefinedException {

		when(orderServices.placeOrder(placeOrderDto)).thenReturn("Order placed successfully");
		ResponseEntity<String> result = orderController.purchaseProducts(placeOrderDto);
		verify(orderServices).placeOrder(placeOrderDto);
		assertEquals("Order placed successfully", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(2)
	@DisplayName(" Order History :Positive Scenario")
	public void getOrderHistory() throws UserDefinedException {

		when(orderServices.getByUserId(1)).thenReturn(orderHistoryList);
		ResponseEntity<List<OrderHistoryResponseDto>> result = orderController.getOrderHistoryByUserId(1);
		verify(orderServices).getByUserId(1);
		assertEquals(orderHistoryList, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@Order(3)
	@DisplayName("Place Order: Negative Scenario")
	public void placeOrder1() throws UserDefinedException {

		when(orderServices.placeOrder(placeOrderDto)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> orderController.purchaseProducts(placeOrderDto));

	}

	@Test
	@Order(4)
	@DisplayName("get orderHistory : Negative Scenario")
	public void orderHistory() throws UserDefinedException {

		when(orderServices.getByUserId(1)).thenThrow(UserDefinedException.class);

		assertThrows(UserDefinedException.class, () -> orderController.getOrderHistoryByUserId(1));

	}

}
