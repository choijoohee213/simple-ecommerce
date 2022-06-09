package com.choijoohee.ecommerce.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choijoohee.ecommerce.cart.dto.CartItem;
import com.choijoohee.ecommerce.cart.dto.CartItemInsertResponse;
import com.choijoohee.ecommerce.cart.repository.CartRepository;
import com.choijoohee.ecommerce.product.dto.Product;
import com.choijoohee.ecommerce.product.exception.SoldOutException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;

	/**
	 * 장바구니의 모든 목록을 반환한다.
	 * 배송 그룹 별로 묶어서 반환하기 위해서 Map을 이용하여 반환한다.
	 * @return
	 */
	@Override
	public Map<String, List<CartItem>> getCartItems() {
		Map<String, List<CartItem>> cartItemsByDeliveryGroup = new HashMap<>();
		for (CartItem item : cartRepository.selectAll()) {
			String deliveryGroup = item.getDeliveryGroup();
			if(!cartItemsByDeliveryGroup.containsKey(deliveryGroup)) {
				cartItemsByDeliveryGroup.put(deliveryGroup, new ArrayList<>());
			}
			cartItemsByDeliveryGroup.get(deliveryGroup).add(item);
		}
		return cartItemsByDeliveryGroup;
	}

	/**
	 * 해당 상품의 재고가 남아있는 지 확인 후,
	 * 재고가 있다면 장바구니에 이미 있던 상품인지 확인한다.
	 * 이미 있던 상품이라면 장바구니의 수량을 하나 늘리고, 아니라면 장바구니에 새로 넣는다.
	 * @param product 장바구니에 넣을 상품 정보
	 */
	@Override
	@Transactional
	public CartItemInsertResponse addCartItem(Product product) {
		CartItem selectedItem = cartRepository.selectById(product.getId());
		if (selectedItem == null) {
			isValidQuantity(product.getQuantity());
			log.debug("장바구니에 없는 상품 - 추가");
			cartRepository.insert(new CartItem(product));
			return new CartItemInsertResponse();
		} else {
			isValidQuantity(product.getQuantity() - selectedItem.getQuantity());
			log.debug("장바구니에 있던 상품 - 수 늘리기");
			selectedItem.setQuantity(selectedItem.getQuantity() + 1);
			cartRepository.updateQuantity(selectedItem);
			return new CartItemInsertResponse(selectedItem.getQuantity());
		}
	}

	/**
	 * 장바구니의 상품의 수량을 update 한다.
	 * @param productId 상품의 번호(id)
	 * @param updatedQuantity 바뀔 수량의 개수
	 */
	@Override
	@Transactional
	public void updateQuantity(int productId, int productQuantity, int updatedQuantity) {
		isValidQuantity(productQuantity - updatedQuantity);
		CartItem selectedItem = cartRepository.selectById(productId);
		selectedItem.setQuantity(updatedQuantity);
		cartRepository.updateQuantity(selectedItem);
	}

	/**
	 * 장바구니의 상품을 삭제한다.
	 * @param productId 상품의 번호(id)
	 */
	@Override
	@Transactional
	public void deleteCartItem(int productId) {
		cartRepository.delete(productId);
	}

	/**
	 * 장바구니 상품의 selected를 수정한다.(toggle)
	 * @param productId 상품 번호(id)
	 */
	@Override
	@Transactional
	public void updateSelected(int productId) {
		cartRepository.updateSelected(productId);
	}

	/**
	 * 상품의 재고(수량)가 남아있는 지 확인한다.
	 * 남아있지 않다면 SoldOutException 예외 발생
	 * @param quantity 수량
	 */
	public void isValidQuantity(int quantity) {
		if (quantity <= 0) {
			log.debug("상품 재고 없음");
			throw new SoldOutException();
		}
	}
}
