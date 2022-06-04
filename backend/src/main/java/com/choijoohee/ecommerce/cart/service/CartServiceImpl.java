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

	/**
	 * 장바구니의 모든 목록을 반환한다.
	 * @return
	 */
	@Override
	public List<CartItemDto> getCartItems() {
		return cartRepository.selectAll();
	}

	/**
	 * 해당 상품의 재고가 남아있는 지 확인 후,
	 * 재고가 있다면 장바구니에 이미 있던 상품인지 확인한다.
	 * 이미 있던 상품이라면 장바구니의 수량을 하나 늘리고, 아니라면 장바구니에 새로 넣는다.
	 * @param productDto 장바구니에 넣을 상품 정보
	 */
	@Override
	public void addCartItem(ProductDto productDto) {
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

	/**
	 * 상품의 재고(수량)가 남아있는 지 확인한다.
	 * 남아있지 않다면 SoldOutException 예외 발생
	 * @param quantity 수량
	 */
	@Override
	public void isValidQuantity(int quantity) {
		if (quantity <= 0) {
			log.debug("상품 재고 없음");
			throw new SoldOutException("상품의 재고가 없습니다.");
		}
	}
}
