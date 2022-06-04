package com.choijoohee.ecommerce.cart.exception;

/**
 * 장바구니에 이미 있는 상품을 다시 추가하려 할 경우 이 예외를 발생시킨다.
 */
public class DuplicateItemException extends RuntimeException {
	public DuplicateItemException() {
		super();
	}

	public DuplicateItemException(String message) {
		super(message);
	}

	public DuplicateItemException(String message, Throwable cause) {
		super(message, cause);
	}
}
