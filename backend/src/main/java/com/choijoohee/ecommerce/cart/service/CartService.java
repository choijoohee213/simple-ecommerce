package com.choijoohee.ecommerce.cart.service;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;

public interface CartService {
	List<CartItemDto> getCartItems();

	void addCartItem(CartItemDto cartItemDto);
}
