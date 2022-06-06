package com.choijoohee.ecommerce.product.repository;

import java.util.List;

import com.choijoohee.ecommerce.product.dto.Product;

public interface ProductRepository {
	List<Product> selectAll();

	Product selectById(int productId);
}
