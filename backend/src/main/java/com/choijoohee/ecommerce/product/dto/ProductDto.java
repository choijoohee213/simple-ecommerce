package com.choijoohee.ecommerce.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
	private String deliveryGroup;
	private String name;
	private int price;
	private int quantity;
}
