package com.choijoohee.ecommerce.cart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.service.CartService;
import com.choijoohee.ecommerce.product.dto.ProductDto;
import com.choijoohee.ecommerce.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {
	private final CartService cartService;
	private final ProductService productService;

	@GetMapping
	public ResponseEntity<List<CartItemDto>> getCartItems() {
		log.debug("장바구니 목록 불러오기");
		return ResponseEntity.ok(cartService.getCartItems());
	}

	@PostMapping("/{productId}")
	public ResponseEntity<String> addCartItem(@PathVariable int productId) {
		log.debug("장바구니에 상품 추가 로직 시작");
		ProductDto product = productService.findProductById(productId);
		cartService.addCartItem(product);
		return new ResponseEntity<>("장바구니에 상품을 넣었습니다!", HttpStatus.OK);
	}
}
