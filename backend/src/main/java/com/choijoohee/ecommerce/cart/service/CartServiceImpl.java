package com.choijoohee.ecommerce.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.exception.DuplicateItemException;
import com.choijoohee.ecommerce.cart.repository.CartRepository;
import com.choijoohee.ecommerce.product.dto.ProductDto;
import com.choijoohee.ecommerce.product.exception.SoldOutException;

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
	public void addCartItem(ProductDto productDto) {
		//장바구니에 이미 존재하는 상품인지 확인
		//재고 확인
		//존재한다면 수량 하나 늘리기
		//없다면 상품 넣기
		CartItemDto selectedItem = cartRepository.selectById(productDto.getId());
		if (selectedItem == null) {
			isValidQuantity(productDto.getQuantity());
			log.debug("장바구니에 없는 상품 - 추가");
			cartRepository.insert(new CartItemDto(productDto.getId(), productDto.getName(), productDto.getPrice(), 1));
		} else {
			log.debug("장바구니에 있던 상품 - 수 늘리기");
			cartRepository.increaseQuantity(selectedItem);
			throw new DuplicateItemException(
				"장바구니에 이미 있는 상품입니다!\n장바구니 수량이 " + (selectedItem.getQuantity() + 1) + "개가 되었습니다.");
		}
	}

	@Override
	public void isValidQuantity(int quantity) {
		if (quantity <= 0) {
			log.debug("상품 재고 없음");
			throw new SoldOutException("상품의 재고가 없습니다.");
		}
	}
}
