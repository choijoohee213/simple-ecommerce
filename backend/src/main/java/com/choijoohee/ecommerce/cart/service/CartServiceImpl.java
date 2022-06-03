package com.choijoohee.ecommerce.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;

	@Override
	public List<CartItemDto> getCartItems() {
		return cartRepository.selectAll();
	}

	@Override
	@Transactional
	public void addCartItem(CartItemDto newItem) {
		//장바구니에 이미 존재하는 상품인지 확인
		//존재한다면 수량 하나 늘리기
		//없다면 상품 넣기
		CartItemDto selectedItem = cartRepository.selectById(newItem.getProductId());
		if (selectedItem == null) {
			log.debug("장바구니에 없는 상품 - 추가");
			cartRepository.insert(newItem);
		} else {
			log.debug("장바구니에 있던 상품 - 수 늘리기");
			cartRepository.increaseQuantity(selectedItem);
		}
	}
}
