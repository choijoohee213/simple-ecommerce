package com.choijoohee.ecommerce.cart.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		return cartRepository.selectAll()
			.stream()
			.collect(
				Collectors.groupingBy(CartItem::getDeliveryGroup,
					Collectors.mapping(Function.identity(), Collectors.toList()))
			);
	}

	/**
	 * 상품 번호로 장바구니에 있는 상품을 찾는다.
	 * @param productId 상품 번호(id)
	 * @return
	 */
	@Override
	public CartItem getCartItem(int productId) {
		return cartRepository.selectById(productId);
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
			if (product.getQuantity() <= 0) {
				log.debug("상품 재고 없음");
				throw new SoldOutException(product.getName(), product.getQuantity());
			}
			log.debug("장바구니에 없는 상품 - 추가");
			cartRepository.insert(new CartItem(product));
			return new CartItemInsertResponse();
		} else {
			log.debug("장바구니에 있던 상품 - 수 늘리기");
			selectedItem.setQuantity(selectedItem.getQuantity() + 1);
			cartRepository.updateQuantity(selectedItem);
			return new CartItemInsertResponse(selectedItem.getQuantity());
		}
	}

	/**
	 * 장바구니의 상품의 수량을 수정한다.
	 * 이 때 재고를 확인하여 충분하지 않다면 예외를 발생시킨다.
	 * @param productId 상품의 번호(id)
	 */
	@Override
	@Transactional
	public void updateQuantity(int productId, int updatedQuantity) {
		CartItem selectedItem = cartRepository.selectById(productId);
		int stockQuantity = cartRepository.selectStockQuantity(productId);
		if (selectedItem.getQuantity() < updatedQuantity && stockQuantity < updatedQuantity) {
			log.debug("상품 재고 없음");
			throw new SoldOutException(selectedItem.getName(), stockQuantity);
		}
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
	 * 장바구니의 모든 상품을 구매한다.
	 * 장바구니의 모든 상품을 조회한 후, 모든 상품의 재고가 충분한지 확인한다.
	 * 충분하지 않을 경우 SoldOutException 발생
	 * 충분할 경우에 장바구니에서 모든 상품 삭제
	 */
	@Override
	@Transactional
	public void payAll() {
		cartRepository.selectAll().forEach(item -> {
			int stockQuantity = cartRepository.selectStockQuantity(item.getProductId());
			if (stockQuantity < item.getQuantity()) {
				throw new SoldOutException(item.getName(), stockQuantity);
			}
		});
		cartRepository.deleteAll();
	}

	/**
	 * 장바구니에서 선택된 상품들만 구매한다.
	 * 구매할 모든 상품의 재고가 충분한지 확인한다.
	 * 충분하지 않다면 SoldOutException을 발생 후 종료
	 * 충분하다면 구매하고 장바구니 목록에서 삭제한다.
	 */
	@Override
	@Transactional
	public void paySelectedItems() {
		cartRepository.selectAll().stream()
			.filter(CartItem::isSelected).forEach(item -> {
				int stockQuantity = cartRepository.selectStockQuantity(item.getProductId());
				if (stockQuantity < item.getQuantity()) {
					throw new SoldOutException(item.getName(), stockQuantity);
				} else {
					cartRepository.delete(item.getProductId());
				}
			});
	}
}
