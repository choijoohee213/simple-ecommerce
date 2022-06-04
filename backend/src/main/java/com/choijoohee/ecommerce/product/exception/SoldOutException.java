package com.choijoohee.ecommerce.product.exception;

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
