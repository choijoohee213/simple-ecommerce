package com.choijoohee.ecommerce.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.choijoohee.ecommerce.product.dto.ProductDto;
import com.choijoohee.ecommerce.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	@Override
	public List<ProductDto> getProducts() {
		return productRepository.selectAll();
	}

	@Override
	public ProductDto findProductById(int productId) {
		return productRepository.selectById(productId);
	}

}
