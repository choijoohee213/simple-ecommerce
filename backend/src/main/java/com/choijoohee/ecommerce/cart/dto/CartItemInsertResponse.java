package com.choijoohee.ecommerce.cart.dto;

import lombok.Getter;

/**
 * 장바구니에 상품을 추가할 때 반환을 할 response 클래스
 * 로직의 경우에 따라 메세지를 다르게 저장한다.
 * 	- 장바구니에 이미 있는 상품의 수량을 증가시키는 로직
 * 	- 장바구니에 새롭게 추가하는 상품의 로직
 */
@Getter
public class CartItemInsertResponse {
	private final String message;
	private static final String DUPLICATE_CART_ITEM_MESSAGE = "장바구니에 이미 있는 상품입니다!\n장바구니 수량이 %d개가 되었습니다.";
	private static final String INSERT_CART_ITEM_SUCCESS_MESSAGE = "장바구니에 상품을 넣었습니다!";

	public CartItemInsertResponse() {
		this.message = INSERT_CART_ITEM_SUCCESS_MESSAGE;
	}

	public CartItemInsertResponse(int updatedQuantity) {
		this.message = String.format(DUPLICATE_CART_ITEM_MESSAGE, updatedQuantity);
	}
}
