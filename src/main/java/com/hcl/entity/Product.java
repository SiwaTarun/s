package com.hcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private int productId;

	@NotNull(message = "product_name cannot be null")
	@Column(name = "product_name")
	private String productName;

	@NotNull(message = "product_category cannot be null")
	@Column(name = "product_category")
	private String productCategory;

	@NotNull(message = "ProductPrice cannot be null")
	@Column(name = "product_price")
	private double productPrice;

	@NotNull(message = "quantity cannot be null")
	@Column(name = "quantity")
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

}
