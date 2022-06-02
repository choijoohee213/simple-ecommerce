# simple-ecommerce

상품 선택부터 장바구니 구매까지의 주문 서비스

<br><br>

# 기능 구현 목록 💻

## 상품 목록

- 상품 목록 출력
  - 상품명, 판매가, 수량
- 상품 별로 장바구니 담기 구현
  - [예외처리] 재고가 0인 상품은 장바구니에 담지 못함

## 장바구니

- 장바구니 상품 목록 출력
  - 동일 그룹으로 묶어서 출력
  - 상품명, 판매가, 구매수량, 구매금액 출력
- 상품의 수량 변경 가능
  - [예외처리] 재고보다 많은 수량을 구매할 수 없음
- 상품 삭제 가능
  - 여러 상품 동시에 삭제 가능
- 결제 예정 금액 제공
  - 선택된 상품이 존재한다면 선택된 상품들만의 합계 금액
  - 선택된 상품들이 없다면 장바구니 내 모든 상품들의 합계 금액
  - 상품 선택 시 마다 결제 예정 금액 갱신
- 구매하기 구현
  - 사용자가 구매한 총 금액 메세지 띄우기
  - 선택된 상품이 존재한다면 선택된 상품들만 구매
  - 선택된 상품들이 없다면 장바구니 내 모든 상품들 구매
  - 구매하기가 완료되면 장바구니 목록에서 제거
