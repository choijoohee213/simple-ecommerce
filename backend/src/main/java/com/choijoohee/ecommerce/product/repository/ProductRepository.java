package com.choijoohee.ecommerce.product.repository;

import java.util.List;

import com.choijoohee.ecommerce.product.dto.ProductDto;

public interface ProductRepository {
	List<ProductDto> selectAll();
}
