# SSG.COM 사전 과제 - 장바구니 및 상품 구매 프로토타입 🛒
REST 구조로 작성한 상품 선택부터 장바구니 구매까지의 주문 서비스
<br><br>

## 1. 개발환경 구성 정보

[Front]

- Vue.js 2.0
- Bootstrap-Vue

[Back]

- Java 8
- Spring Boot 2.7.0
- Maven
- MySQL 8.0.28
- Mybatis

## 2. 데이터베이스 ERD 다이어그램
<img width="730" alt="image" src="https://user-images.githubusercontent.com/60915285/194112791-ba363c2e-2294-4b43-a558-0374cd16dcc7.png">


## 3. 화면별 작동과정

### 상품 목록 화면
<img width="729" alt="image" src="https://user-images.githubusercontent.com/60915285/194112876-349f5bf2-7cf2-4702-9e79-ef14278fced9.png">


상품 목록을 데이터베이스에서 조회하여 모두 출력한다. 상품의 사진, 이름, 가격, 배송 그룹 종류 정보를 보여준다. 또한 상품 재고 개수에 따라 상품 이름에 밑줄을 그어 품절 여부를 알려준다. 상품 별로 장바구니 추가 버튼이 있어서 장바구니에 추가할 수 있다.

### 상품 목록 화면 - 장바구니에 새로운 상품 추가
<img width="728" alt="image" src="https://user-images.githubusercontent.com/60915285/194112934-1d1db77d-9b4f-40bb-bb58-93e772546049.png">

현재 허쉬 초코멜로쿠키 45g 을 장바구니에 추가하였고, 추가되었다는 알림을 화면에 출력한다. 재고가 있는 유효한 상품일 경우에만 추가할 수 있다.추가한 상품은 장바구니 데이터베이스에 저장된다.


### 상품 목록 화면 - 장바구니에 있던 상품 추가
<img width="734" alt="image" src="https://user-images.githubusercontent.com/60915285/194112989-1af0fc2a-da71-4a4d-ad2d-531fc26c4690.png">

장바구니에 있는 똑같은 상품을 다시 추가한다면, 장바구니에 이미 있다고 알려준 후에 수량을 1 증가시킨다.


### 상품 화면 - 재고 부족
<img width="745" alt="image" src="https://user-images.githubusercontent.com/60915285/194113021-2f50f36a-95a0-40ed-a8a4-7aa93ac2f59a.png">
 
재고가 부족한 품절 상태의 상품을 장바구니에 추가하려하면, 재고가 부족하다는 알림을 출력한 다.


### 장바구니 화면
<img width="700" alt="image" src="https://user-images.githubusercontent.com/60915285/194113739-bc3a847f-de7d-4d50-8c04-5f52cc9fb273.png">

장바구니 화면에서는 추가되었던 상품들 목록이 배송 그룹별로 보여진다. 각 상품의 사진, 이름, 총 금액, 구매하려는 수량을 출력한다.또한 각 상품과 배송 그룹, 전체 선택을 할 수 있는 체크박스가 있다.


### 장바구니 화면 - 배송 그룹 선택
<img width="746" alt="image" src="https://user-images.githubusercontent.com/60915285/194113933-b97144f8-9049-4cd7-853b-85c74fd7840d.png">

배송 그룹 체크박스를 체크하면 그룹 내의 모든 상품이 체크되고,
결제 예정 금액은 체크된 상품들의 합계이고, 체크된 상품이 하나도 없다면 장바구니 내 모든 상 품의 금액을 출력한다.


### 장바구니 화면 - 전체선택 체크
<img width="700" alt="image" src="https://user-images.githubusercontent.com/60915285/194114027-285f23f6-d327-4eb2-9d4c-08ec902fd544.png">

전체선택을 체크하면 장바구니 내의 모든 상품이 체크된다.


### 장바구니 화면 - 재고 부족
<img width="748" alt="image" src="https://user-images.githubusercontent.com/60915285/194114066-97f41374-1a16-4e57-9532-94a0366bd904.png">

+와 -버튼, 수량을 직접 입력하여 장바구니 상품 수량을 변경할 수 있다.
바뀐 상품 수량은 유지되며, 만약 상품의 재고보다 많은 수량으로 변경하면 재고가 부족하다는 알림을 띄우며 바뀌지 않는다.


### 장바구니 화면 - 선택 상품 없이 선택 삭제했을 때
<img width="766" alt="image" src="https://user-images.githubusercontent.com/60915285/194114149-d53bace7-d419-4640-9f0c-3d903a0d5797.png">

선택된 상품이 하나도 없는데, 선택 삭제 버튼을 누른다면 선택된 상품이 없다는 알림을 출력한 다.


### 장바구니 화면 - 전체 구매
<img width="763" alt="image" src="https://user-images.githubusercontent.com/60915285/194114210-53f7098e-5f27-426e-b5e7-d9ec3829e132.png">

선택된 상품이 하나도 없이 구매하기를 누르면, 장바구니 내의 모든 상품이 구매된다. 구매는 각 상품의 구매하려는 수량과 재고를 확인한 후에 진행한다.구매에 성공했다는 알림과 함께 구매금액을 출력한다.


### 장바구니 화면 - 선택 구매
<img width="769" alt="image" src="https://user-images.githubusercontent.com/60915285/194114257-5cc0c3e2-2f73-466b-b8df-a30ce319ab84.png">

선택된 상품이 하나라도 있다면, 선택된 상품들만 구매한다.


### 장바구니 화면 - 빈 화면
<img width="768" alt="image" src="https://user-images.githubusercontent.com/60915285/194114339-0a34a89c-b1f8-434d-aced-5326d5c350b9.png">

장바구니가 비어 있는 화면이다.

<br><br>
## 요구사항 정의 💻

### 상품 목록

- 상품 목록 출력
  - 상품명, 판매가, 수량, 배송그룹
- 상품 별로 장바구니 담기 구현
  - [예외처리] 재고가 0인 상품은 장바구니에 담지 못함
  - [예외처리] 이미 장바구니에 있는 상품은  수량을 1 증가 시키기

### 장바구니

- 장바구니 상품 목록 출력
  - 동일 배송 그룹으로 묶어서 출력
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
