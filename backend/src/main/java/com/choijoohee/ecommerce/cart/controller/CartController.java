package com.choijoohee.ecommerce.cart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choijoohee.ecommerce.cart.dto.CartItem;
import com.choijoohee.ecommerce.cart.dto.CartItemInsertResponse;
import com.choijoohee.ecommerce.cart.service.CartService;
import com.choijoohee.ecommerce.product.dto.Product;
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

	/**
	 * 배송 그룹 별 장바구니 목록을 조회하여 반환한다.
	 * @return
	 */
	@GetMapping
	public ResponseEntity<Map<String, List<CartItem>>> getCartItems() {
		log.debug("장바구니 목록 불러오기");
		return ResponseEntity.ok(cartService.getCartItems());
	}

	/**
	 * 장바구니에 상품을 추가한다.
	 * 장바구니에 이미 있던 상품과 새롭게 추가하는 상품을 구분하여 처리한다.
	 * @param productId 상품 번호(id)
	 * @return
	 */
	@PostMapping("/{productId}")
	public ResponseEntity<CartItemInsertResponse> addCartItem(@PathVariable int productId) {
		log.debug("장바구니에 상품 추가 로직 시작");
		Product product = productService.findProductById(productId);
		return new ResponseEntity<>(cartService.addCartItem(product), HttpStatus.OK);
	}

	/**
	 * 장바구니의 상품의 수량을 바꾼다.
	 * +/- 버튼을 누르면 동작하는 메서드
	 * @param productId 상품의 번호(id)
	 * @param quantity 바뀔 수량의 개수
	 * @return
	 */
	@PutMapping("/{productId}/{quantity}")
	public ResponseEntity<?> updateQuantity(@PathVariable int productId, @PathVariable int quantity) {
		log.debug("장바구니 상품 수량 업데이트");
		cartService.updateQuantity(productId, quantity);
		return ResponseEntity.ok().build();
	}

	/**
	 * 상품 번호에 해당하는 장바구니의 상품을 삭제한다.
	 * @param productId 상품 번호(id)
	 * @return
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteCartItem(@PathVariable int productId) {
		log.debug("장바구니 상품 삭제");
		cartService.deleteCartItem(productId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{productId}/selected")
	public ResponseEntity<?> toggleSelectedItem(@PathVariable int productId) {
		log.debug("장바구니 상품 체크 토글");
		cartService.updateSelected(productId);
		return ResponseEntity.ok().build();
	}
}
