package com.choijoohee.ecommerce.cart.service;

import java.util.List;

import com.choijoohee.ecommerce.cart.dto.CartItemDto;
import com.choijoohee.ecommerce.cart.dto.CartItemInsertResponse;
import com.choijoohee.ecommerce.product.dto.ProductDto;

public interface CartService {
	List<CartItemDto> getCartItems();

	CartItemInsertResponse addCartItem(ProductDto productDto);

	void isValidQuantity(int quantity);

}
