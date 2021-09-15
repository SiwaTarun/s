package com.hcl.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.OrderHistoryResponseDto;
import com.hcl.dto.PlaceOrderDto;
import com.hcl.exception.UserDefinedException;
import com.hcl.services.IOrderService;

@RestController
@RequestMapping("orders")
public class OrderController {

	@Autowired
	private IOrderService orderServices;

	@PostMapping
	public ResponseEntity<String> purchaseProducts(@RequestBody PlaceOrderDto placeOrderDto)
			throws UserDefinedException {
		return new ResponseEntity<String>(orderServices.placeOrder(placeOrderDto), HttpStatus.OK);
	}

	@GetMapping("/{user_id}")
	public ResponseEntity<List<OrderHistoryResponseDto>> getOrderHistoryByUserId(@PathVariable int user_id)
			throws UserDefinedException {
		List<OrderHistoryResponseDto> ls = new ArrayList<OrderHistoryResponseDto>();
		ls = orderServices.getByUserId(user_id);
		return new ResponseEntity<List<OrderHistoryResponseDto>>(ls, HttpStatus.OK);
	}

}
