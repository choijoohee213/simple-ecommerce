package com.choijoohee.ecommerce.cart.dto;

import com.choijoohee.ecommerce.product.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 장바구니 상품을 위한 dto로, cart 테이블의 컬럼명과 일치한다.
 */
@Data
@AllArgsConstructor
public class CartItem {
	private int productId;
	private String deliveryGroup;
	private String name;
	private int price;
	private int quantity;
	private String image;
	private boolean selected;

	public CartItem(Product product) {
		this.productId = product.getId();
		this.deliveryGroup = product.getDeliveryGroup();
		this.name = product.getName();
		this.price = product.getPrice();
		this.quantity = 1;
		this.selected = false;
	}

	public CartItem(int productId, CartItem selectedItem) {
		this.productId = productId;
		this.deliveryGroup = selectedItem.getDeliveryGroup();
		this.name = selectedItem.getName();
		this.price = selectedItem.getPrice();
		this.quantity = selectedItem.getQuantity();
		this.selected = selectedItem.isSelected();
	}
}
