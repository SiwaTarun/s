package com.hcl.services;

import java.util.List;

import com.hcl.dto.OrderHistoryResponseDto;
import com.hcl.dto.PlaceOrderDto;
import com.hcl.entity.Orders;
import com.hcl.exception.UserDefinedException;

public interface IOrderService {
	Orders addOrders(Orders orders) throws UserDefinedException;

	String placeOrder(PlaceOrderDto placeOrderDto) throws UserDefinedException;

	List<OrderHistoryResponseDto> getByUserId(int user_id) throws UserDefinedException;
}
