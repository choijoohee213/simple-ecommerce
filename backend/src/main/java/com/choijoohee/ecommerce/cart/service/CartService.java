package com.choijoohee.ecommerce.cart.service;

import java.util.List;
import java.util.Map;

import com.choijoohee.ecommerce.cart.dto.CartItem;
import com.choijoohee.ecommerce.cart.dto.CartItemInsertResponse;
import com.choijoohee.ecommerce.product.dto.Product;

public interface CartService {
	Map<String, List<CartItem>> getCartItems();

	CartItemInsertResponse addCartItem(Product product);

	void updateQuantity(int productId, int updatedQuantity);

	void deleteCartItem(int productId);

	void updateSelected(int productId);
}
