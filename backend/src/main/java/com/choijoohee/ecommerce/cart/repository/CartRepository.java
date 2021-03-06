package com.choijoohee.ecommerce.cart.repository;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItem;

public interface CartRepository {
	List<CartItem> selectAll();

	CartItem selectById(int productId);

	void insert(CartItem cartItem);

	void updateQuantity(CartItem cartItem);

	void delete(int productId);

	void deleteAll();

	void updateSelected(int productId);

	int selectStockQuantity(int productId);
}
