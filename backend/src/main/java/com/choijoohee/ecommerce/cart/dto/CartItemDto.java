package com.choijoohee.ecommerce.cart.dto;

import lombok.Data;

@Data
public class CartItemDto {
	private String name;
	private int price;
	private int quantity;
}
