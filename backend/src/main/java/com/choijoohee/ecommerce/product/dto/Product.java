package com.choijoohee.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
	private int id;
	private String deliveryGroup;
	private String name;
	private int price;
	private int quantity;
}
