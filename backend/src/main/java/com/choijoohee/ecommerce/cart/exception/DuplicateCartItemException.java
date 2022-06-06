package com.choijoohee.ecommerce.cart.exception;

/**
 * 장바구니에 이미 있는 상품을 다시 추가하려 할 경우 이 예외를 발생시킨다.
 */
public class DuplicateCartItemException extends RuntimeException {
	private static final String DUPLICATE_CART_ITEM_MESSAGE = "장바구니에 이미 있는 상품입니다!\n장바구니 수량이 %d개가 되었습니다.";

	public DuplicateCartItemException(int updatedQuantity) {
		super(String.format(DUPLICATE_CART_ITEM_MESSAGE, updatedQuantity));
	}
}
