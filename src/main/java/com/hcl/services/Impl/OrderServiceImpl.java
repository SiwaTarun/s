package com.hcl.services.Impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.dto.OrderHistoryResponseDto;
import com.hcl.dto.PlaceOrderDto;
import com.hcl.entity.Cart;
import com.hcl.entity.Orders;
import com.hcl.entity.User;
import com.hcl.exception.UserDefinedException;
import com.hcl.repositry.ICartDAO;
import com.hcl.repositry.IOrderDAO;
import com.hcl.repositry.IProductDAO;
import com.hcl.repositry.IUserDAO;
import com.hcl.services.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IOrderDAO orderDAO;

	@Autowired
	private ICartDAO cartDAO;

	@Autowired
	private IProductDAO productDAO;

	double totSum;
	double productPrice;

	@Transactional
	@Override
	public String placeOrder(PlaceOrderDto placeOrderDto) throws UserDefinedException {
		int user_id = placeOrderDto.getUserId();
		if (userDAO.findById(user_id).isPresent()) {
			if (!cartDAO.findByUserId(user_id).isEmpty()) {
				List<Cart> itemsList = cartDAO.findByUserId(user_id);
				itemsList.stream().forEach(list -> {
					productPrice = productDAO.findByProductId(list.getProduct().getProductId());
					double sum = productPrice * list.getQuantity();
					totSum += sum;
				});
				double totPrice = totSum;
				Orders o = new Orders();
				User u = new User();
				u.setUserId(user_id);
				o.setTotalPrice(totPrice);
				o.setUser(u);
				LocalDate date = LocalDate.now();
				o.setDate(date);
				addOrders(o);
				totSum = 0;
				cartDAO.deleteByUser(u);
				return "Order placed Successfully";
			} else {
				throw new UserDefinedException("User has nothing in cart");
			}
		} else {
			throw new UserDefinedException("User not found");
		}
	}

	@Override
	public Orders addOrders(Orders orders) throws UserDefinedException {
		return orderDAO.saveAndFlush(orders);
	}

	// to get the order history
	@Override
	public List<OrderHistoryResponseDto> getByUserId(int user_id) throws UserDefinedException {
		List<OrderHistoryResponseDto> list = new ArrayList<OrderHistoryResponseDto>();
		List<Orders> response = new ArrayList<Orders>();

		if (userDAO.findById(user_id).isPresent()) {
			if (!orderDAO.findByUserId(user_id).isEmpty()) {
				response = orderDAO.findByUserId(user_id);

			} else
				throw new UserDefinedException("This user has no order history");

		} else {
			throw new UserDefinedException("No user available");
		}
		response.stream().forEach(od -> {
			OrderHistoryResponseDto responseDto = new OrderHistoryResponseDto();
			responseDto.setOrderId(od.getOrderId());
			responseDto.setTotalPrice(od.getTotalPrice());
			LocalDate date = LocalDate.now();
			responseDto.setDate(date);
			responseDto.setUserId(od.getUser().getUserId());
			list.add(responseDto);
		});
		return list;

	}
}
