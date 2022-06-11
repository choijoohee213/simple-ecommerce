package com.choijoohee.ecommerce.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choijoohee.ecommerce.product.dto.Product;
import com.choijoohee.ecommerce.product.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 상품에 관련한 요청을 처리한 후에 응답을 리턴하는 RestController 클래스이다.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService;

	/**
	 * 상품 목록을 조회하여 리스트로 반환한다.
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {
		log.debug("상품 목록 불러오기");
		return ResponseEntity.ok(productService.getProducts());
	}
}
