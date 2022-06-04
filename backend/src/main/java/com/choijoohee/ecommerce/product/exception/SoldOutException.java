package com.choijoohee.ecommerce.product.exception;

/**
 * 상품의 재고가 없어서 품절 상태에서 장바구니에 넣으려하거나 구매를 하려할 경우 발생하는 예외
 */
public class SoldOutException extends RuntimeException {
	public SoldOutException() {
	}

	public SoldOutException(String message) {
		super(message);
	}

	public SoldOutException(String message, Throwable cause) {
		super(message, cause);
	}
}
