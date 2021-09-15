package com.hcl.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "order_id")
	private int orderId;

	@NotNull(message = "Datetime cannot be null")
	@Column(name = "datetime")
	LocalDate date = LocalDate.now();

	@NotNull(message = "TotalPrice cannot be null")
	@Column(name = "total_price")
	private double totalPrice;

	@ManyToOne
	private User user;

}
