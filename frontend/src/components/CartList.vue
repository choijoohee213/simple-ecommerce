<template>
  <div class="my-5">
    <b-container>
      <b-row v-if="this.groups.length > 0" class="mb-3">
        <b-col>
          <b-row>
            <b-col cols="1">
              <input type="checkbox" id="allCheckBox" v-model="allChecked" @click="checkAll($event.target.checked)" />
            </b-col>
            <b-col class="text-left">
              <label for="allCheckBox">
                <b>전체선택</b>
              </label>
            </b-col>
            <b-col cols="2">
              <b-btn variant="success" @click="discardCheckedItems()">선택삭제 <b-icon icon="cart-x-fill"></b-icon></b-btn>
            </b-col>
          </b-row>
        </b-col>
      </b-row>

      <b-row v-for="(deliveryGroup, group) in cartItems" :key="group" class="groupRow">
        <b-col>
          <b-row style="border-bottom: solid green">
            <b-col cols="1"><input type="checkbox" :id="'groupCheckBox_' + group" v-model="groupChecked[group]" @click="checkGroupAll($event.target.checked, group)" /></b-col>
            <b-col cols="8">
              <h4 class="text-left">
                <label :for="'groupCheckBox_' + group">
                  <b>{{ group }}</b>
                </label>
              </h4>
            </b-col>
            <b-col></b-col>
          </b-row>

          <b-row v-for="(item, index) in deliveryGroup" :key="index" style="border-bottom: solid 0.05em grey">
            <b-col cols="1">
              <input type="checkbox" :value="item.name" v-model="item.selected" @change="checkItem($event.target.checked, item)" />
            </b-col>
            <b-col cols="1">
              <b-img :src="item.image" width="80px" height="80px" class="mt-2 mb-2"></b-img>
            </b-col>
            <b-col cols="5" class="text-left ml-3">{{ item.name }}</b-col>
            <b-col cols="2" class="text-right">{{ (item.quantity * item.price) | money }} 원</b-col>
            <b-col>
              <b-button variant="warning" @click="decreaseQuantity(item)">-</b-button>
              <span class="pl-2 pr-2">
                <b-input type="text" v-model="item.quantity" style="width: 23%; display: inline" class="text-center" @change="changeQuantity(item)" />
              </span>
              <b-button variant="warning" @click="increaseQuantity(item)">+</b-button>
              <b-button class="ml-1" variant="white" @click="discardItem(item)"><b-icon icon="trash-fill" variant="danger"></b-icon></b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row v-if="this.groups.length > 0" class="buyRow">
        <b-col cols="2" class="text-right"> 결제예정금액 </b-col>
        <b-col cols="2" class="text-left" style="font-size: 2em; color: orange">
          <span v-if="checkedItemCnt > 0">
            <b>{{ chekcedAmountOfPayment | money }}원</b>
          </span>
          <span v-else>
            <b>{{ totalAmountOfPayment | money }}원</b>
          </span>
        </b-col>
        <b-col cols="2">
          <b-button variant="warning" @click="order" class="mr-4"> 구매하기 <b-icon icon="credit-card"></b-icon></b-button>
        </b-col>
      </b-row>
      <h5 v-else>장바구니가 비어있습니다.</h5>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { updateQuantity, payAll } from "@/api/cart.js";

const cartStore = "cartStore";

export default {
  name: "CartList",
  data() {
    return {
      checkedItemCnt: 0,
      checkedGroupCnt: 0,
      groupChecked: {},
      totalAmountOfPayment: 0,
      chekcedAmountOfPayment: 0,
    };
  },
  computed: {
    ...mapState(cartStore, ["cartItems"]),
    allChecked() {
      return this.checkedGroupCnt === this.groups.length;
    },
    groups() {
      return Object.keys(this.cartItems);
    },
  },
  created() {
    this.getCartItems().then(() => this.initCheckedState());
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems", "plusQuantity", "minusQuantity", "deleteCartItem", "changeSelectedItem", "paySelectedItems"]),
    //그룹 체크박스 상태 및 전체선택 체크박스 상태를 초기화하고 결제예정금액을 계산한다.
    async initCheckedState() {
      this.checkedItemCnt = 0;
      this.checkedGroupCnt = 0;
      this.totalAmountOfPayment = 0;
      this.chekcedAmountOfPayment = 0;
      for (let g in this.groups) {
        let groupName = this.groups[g];
        let checkable = true;
        for (let i in this.cartItems[groupName]) {
          let item = this.cartItems[groupName][i];
          this.totalAmountOfPayment += item.quantity * item.price;
          if (!item.selected) {
            checkable = false;
          } else {
            this.chekcedAmountOfPayment += item.quantity * item.price;
            this.checkedItemCnt++;
          }
        }
        this.groupChecked[groupName] = checkable;
        if (this.groupChecked[groupName]) this.checkedGroupCnt++;
      }
    },
    //사용자가 직접 상품의 수량을 입력하여 수정하였을 때 호출되는 메서드
    async changeQuantity(item) {
      if (item.quantity < 1) {
        item.quantity = 1;
      }
      await updateQuantity(item.productId, item.quantity, (error) => {
        alert(error.response.data);
      });
      await this.getCartItems();
      this.initCheckedState();
    },
    //사용자가 +버튼을 누를 때 호출되는 메서드로, 수량을 1증가시키고 결제예정금액을 계산.
    async increaseQuantity(item) {
      let success = await this.plusQuantity(item.productId);
      if (success) {
        if (item.selected) this.chekcedAmountOfPayment += item.price;
        this.totalAmountOfPayment += item.price;
      }
    },
    //사용자가 -버튼을 누를 때 호출되는 메서드로, 수량을 1감소시키고 결제예정금액을 계산.
    decreaseQuantity(item) {
      if (item.quantity === 1) return;
      if (item.selected) this.chekcedAmountOfPayment -= item.price;
      this.totalAmountOfPayment -= item.price;
      this.minusQuantity(item.productId);
    },
    //전체선택 체크박스를 눌렀을때 호출되는 메서드로, 장바구니의 모든 체크박스 상태를 똑같이 변경
    checkAll(checked) {
      for (let g in this.groups) {
        this.checkGroupAll(checked, this.groups[g]);
      }
    },
    //그룹 체크박스를 눌렀을 때 호출되는 메서드로, 그룹 내 모든 상품의 체크박스 상태를 똑같이 변경 및 결제예정금액 계산
    checkGroupAll(checked, groupName) {
      this.groupChecked[groupName] = checked;
      if (!checked) this.checkedGroupCnt--;
      else this.checkedGroupCnt++;
      for (let i in this.cartItems[groupName]) {
        let item = this.cartItems[groupName][i];
        if (item.selected != checked) {
          if (!checked) {
            this.checkedItemCnt--;
            this.chekcedAmountOfPayment -= item.quantity * item.price;
          } else {
            this.checkedItemCnt++;
            this.chekcedAmountOfPayment += item.quantity * item.price;
          }
          this.changeSelectedItem(item.productId);
        }
      }
    },
    //상품 하나의 체크박스를 눌렀을 때 호출되는 메서드로, 다른 체크박스 상태에 변경사항이 생기는지 확인 및 결제예정금액 계산
    checkItem(checked, item) {
      this.changeSelectedItem(item.productId);
      if (!checked) {
        if (this.groupChecked[item.deliveryGroup]) {
          this.checkedGroupCnt--;
          this.groupChecked[item.deliveryGroup] = false;
        }
        this.checkedItemCnt--;
        this.chekcedAmountOfPayment -= item.quantity * item.price;
        return;
      }
      this.checkedItemCnt++;
      this.chekcedAmountOfPayment += item.quantity * item.price;
      for (let i in this.cartItems[item.deliveryGroup]) {
        if (!this.cartItems[item.deliveryGroup][i].selected) {
          return;
        }
      }
      this.checkedGroupCnt++;
      this.groupChecked[item.deliveryGroup] = true;
    },
    //상품 하나를 삭제할 때 호출되는 메서드로, 체크박스 상태들을 갱신하고 결제예정금액 또한 갱신
    async discardItem(item) {
      this.totalAmountOfPayment -= item.quantity * item.price;
      if (item.selected) {
        this.checkedItemCnt--;
        this.chekcedAmountOfPayment -= item.quantity * item.price;
      }
      let isCheckedGroup = this.groupChecked[item.deliveryGroup];
      await this.deleteCartItem(item.productId).then(() => {
        if (!this.cartItems[item.deliveryGroup]) {
          if (isCheckedGroup) {
            this.checkedGroupCnt--;
          }
          return;
        }
        for (let i in this.cartItems[item.deliveryGroup]) {
          if (!this.cartItems[item.deliveryGroup][i].selected) {
            return;
          }
        }
        if (!isCheckedGroup) {
          this.checkedGroupCnt++;
          this.groupChecked[item.deliveryGroup] = true;
        }
      });
    },
    //체크박스가 선택된 여러 상품들을 삭제할 때 호출되는 메서드
    async discardCheckedItems() {
      if (this.checkedItemCnt === 0) {
        alert("선택된 상품이 없습니다.");
        return;
      }
      let deleted = [];
      for (let g in this.groups) {
        let groupName = this.groups[g];
        for (let i in this.cartItems[groupName]) {
          if (this.cartItems[groupName][i].selected) {
            deleted.push(this.cartItems[groupName][i]);
          }
        }
      }
      for (let i in deleted) {
        await this.discardItem(deleted[i]);
      }
    },
    //구매하기를 눌렀을 때 호출되는 메서드로, 재고확인을 통해 구매가 가능한지 확인한 후 장바구니 목록에서 삭제
    //체크박스를 체크한 것이 하나도 없을 경우 전체 구매, 있다면 체크한 상품들만 구매
    async order() {
      if (this.checkedItemCnt == 0) {
        let amountOfPayment = this.totalAmountOfPayment;
        await payAll(
          () => {
            alert("구매 성공! (결제금액 : " + amountOfPayment + " 원)");
            this.getCartItems();
          },
          (error) => alert(error.response.data)
        );
      } else {
        await this.paySelectedItems(this.chekcedAmountOfPayment);
        await this.initCheckedState();
      }
    },
  },
  filters: {
    money: function (value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
.row {
  align-items: center;
}

.groupRow {
  margin-bottom: 9%;
  padding-bottom: 1%;
  padding-top: 1%;
}

.buyRow {
  justify-content: right;
}
</style>
