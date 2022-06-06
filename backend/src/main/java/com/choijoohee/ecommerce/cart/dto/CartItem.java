package com.choijoohee.ecommerce.cart.dto;

import com.choijoohee.ecommerce.product.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
	private int productId;
	private String name;
	private int price;
	private int quantity;

	public CartItem(Product product) {
		this.productId = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = 1;
	}

	public CartItem(int productId, CartItem selectedItem) {
		this.productId = productId;
		this.name = selectedItem.getName();
		this.price = selectedItem.getPrice();
		this.quantity = selectedItem.getQuantity();
	}
}
