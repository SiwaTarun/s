package com.hcl.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponseDto {

	private int orderId;
	private LocalDate date = LocalDate.now();
	private double totalPrice;
	private int userId;
}
