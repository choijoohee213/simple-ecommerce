package com.choijoohee.ecommerce.product.service;

import java.util.List;

import com.choijoohee.ecommerce.product.dto.Product;

public interface ProductService {
	List<Product> getProducts();

	Product findProductById(int productId);
}
