package com.choijoohee.ecommerce.product.service;

import java.util.List;

import com.choijoohee.ecommerce.product.dto.ProductDto;

public interface ProductService {
	List<ProductDto> getProducts();

	ProductDto findProductById(int productId);
}
