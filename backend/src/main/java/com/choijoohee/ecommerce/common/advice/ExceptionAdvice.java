package com.choijoohee.ecommerce.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.choijoohee.ecommerce.cart.exception.DuplicateCartItemException;
import com.choijoohee.ecommerce.product.exception.SoldOutException;

@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * 상품의 재고가 없을 경우 SoldOutException 이 발생하고 이를 처리한다.
	 *
	 * @param exception 품절 예외
	 * @return
	 */
	@ExceptionHandler(SoldOutException.class)
	public ResponseEntity<String> soldOutException(SoldOutException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * 상품을 장바구니에 추가할 때 해당 상품이 장바구니에 이미 있을 경우
	 * DuplicateItemException 이 발생하고 이를 처리한다.
	 *
	 * @param exception 장바구니에 이미 있을 때 예외
	 * @return
	 */
	@ExceptionHandler(DuplicateCartItemException.class)
	public ResponseEntity<String> duplicateItemException(DuplicateCartItemException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
	}
}
