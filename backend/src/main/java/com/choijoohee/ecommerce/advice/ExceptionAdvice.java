package com.choijoohee.ecommerce.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.choijoohee.ecommerce.cart.exception.DuplicateItemException;
import com.choijoohee.ecommerce.product.exception.SoldOutException;

@RestControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(SoldOutException.class)
	public ResponseEntity<String> soldOutException(SoldOutException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateItemException.class)
	public ResponseEntity<String> duplicateItemException(DuplicateItemException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	}
}
