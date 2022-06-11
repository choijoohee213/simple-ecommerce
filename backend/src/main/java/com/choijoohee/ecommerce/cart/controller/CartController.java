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
import com.choijoohee.ecommerce.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 장바구니에 관련한 요청을 처리한 후에 응답을 리턴하는 RestController 클래스이다.
 */
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
		return new ResponseEntity<>(cartService.addCartItem(productService.findProductById(productId)), HttpStatus.OK);
	}

	/**
	 * 장바구니 상품의 수량을 수정한다.
	 * +/- 버튼이 아닌 사용자가 값을 직접 바꿀 경우 호출된다.
	 * @param productId 상품 번호(id)
	 * @param quantity 바꿀 상품 수량
	 */
	@PutMapping("{productId}/{quantity}")
	public ResponseEntity<HttpStatus> updateQuantity(@PathVariable int productId, @PathVariable int quantity) {
		log.debug("장바구니 상품 수량 수정 - 사용자가 직접 값 입력");
		log.debug(productId + " " + quantity);
		int beforeQuantity = cartService.getCartItem(productId).getQuantity();
		if (beforeQuantity != quantity) {
			cartService.updateQuantity(productId, quantity);
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * 장바구니의 상품의 수량을 증가시킨다.
	 * + 버튼을 누르면 동작하는 메서드
	 * @param productId 상품의 번호(id)
	 * @return
	 */
	@PutMapping("/{productId}/plus")
	public ResponseEntity<HttpStatus> increaseQuantity(@PathVariable int productId) {
		log.debug("장바구니 상품 수량 증가");
		int beforeQuantity = cartService.getCartItem(productId).getQuantity();
		cartService.updateQuantity(productId, beforeQuantity + 1);
		return ResponseEntity.ok().build();
	}

	/**
	 * 장바구니의 상품의 수량을 감소시킨다.
	 * - 버튼을 누르면 동작하는 메서드
	 * @param productId 상품의 번호(id)
	 * @return
	 */
	@PutMapping("/{productId}/minus")
	public ResponseEntity<HttpStatus> decreaseQuantity(@PathVariable int productId) {
		log.debug("장바구니 상품 수량 감소");
		int beforeQuantity = cartService.getCartItem(productId).getQuantity();
		cartService.updateQuantity(productId, beforeQuantity - 1);
		return ResponseEntity.ok().build();
	}

	/**
	 * 상품 번호에 해당하는 장바구니의 상품을 삭제한다.
	 * @param productId 상품 번호(id)
	 * @return
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable int productId) {
		log.debug("장바구니 상품 삭제");
		cartService.deleteCartItem(productId);
		return ResponseEntity.ok().build();
	}

	/**
	 * 장바구니 상품의 체크박스 체크 여부가 바뀔경우 호출되는 메서드이다.
	 * 체크여부를 토글하여 저장한다.
	 * @param productId selected 를 토글할 상품 번호(id)
	 * @return
	 */
	@PutMapping("/{productId}/selected")
	public ResponseEntity<HttpStatus> toggleSelectedItem(@PathVariable int productId) {
		log.debug("장바구니 상품 체크 토글");
		cartService.updateSelected(productId);
		return ResponseEntity.ok().build();
	}

	/**
	 * 장바구니의 모든 상품을 구매한다.
	 * 재고 확인 후 유효하다면 장바구니 목록을 비운다.
	 * @return
	 */
	@GetMapping("/pay")
	public ResponseEntity<HttpStatus> payAll() {
		log.debug("장바구니의 모든 상품 구매");
		cartService.payAll();
		return ResponseEntity.ok().build();
	}

	/**
	 * 장바구니에서 선택된 상품들만 구매한다.
	 * 재고 확인 후 유효하다면 장바구니 목록에서 제거한다.
	 * @return
	 */
	@GetMapping("/pay/selected")
	public ResponseEntity<HttpStatus> paySelected() {
		log.debug("장바구니에서 선택한 상품 구매");
		cartService.paySelectedItems();
		return ResponseEntity.ok().build();
	}
}
