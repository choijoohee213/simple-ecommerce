package com.choijoohee.ecommerce.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;

	@Override
	public List<CartItemDto> getCartItems() {
		return cartRepository.selectAll();
	}
}
