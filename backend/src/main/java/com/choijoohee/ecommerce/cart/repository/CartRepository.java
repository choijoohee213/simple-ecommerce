package com.choijoohee.ecommerce.cart.repository;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;

public interface CartRepository {
	List<CartItemDto> selectAll();
}
