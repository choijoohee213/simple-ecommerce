package com.choijoohee.ecommerce.cart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;

	@GetMapping
	public ResponseEntity<List<CartItemDto>> getCartItems() {
		log.debug("장바구니 목록 불러오기");
		return ResponseEntity.ok(cartService.getCartItems());
	}
}
