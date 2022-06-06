package com.choijoohee.ecommerce.cart.repository;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItem;

public interface CartRepository {
	List<CartItem> selectAll();

	CartItem selectById(int productId);

	void insert(CartItem cartItem);

	void increaseQuantity(CartItem cartItem);
}
