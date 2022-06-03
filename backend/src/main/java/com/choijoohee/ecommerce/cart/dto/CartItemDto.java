package com.choijoohee.ecommerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
	private int productId;
	private String name;
	private int price;
	private int quantity;
}
