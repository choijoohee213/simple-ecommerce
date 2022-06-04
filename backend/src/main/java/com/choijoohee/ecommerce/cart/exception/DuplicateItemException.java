package com.choijoohee.ecommerce.cart.exception;

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
