package com.choijoohee.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 상품 정보를 위한 dto로, product 테이블의 컬럼명과 일치한다.
 */
@Data
@AllArgsConstructor
public class Product {
	private int id;
	private String deliveryGroup;
	private String name;
	private int price;
	private int quantity;
	private String image;
}
