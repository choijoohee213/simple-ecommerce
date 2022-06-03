package com.choijoohee.ecommerce.cart.repository;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;

public interface CartRepository {
	List<CartItemDto> selectAll();

	CartItemDto selectById(int productId);

	void insert(CartItemDto cartItemDto);

	void increaseQuantity(CartItemDto cartItemDto);
}
