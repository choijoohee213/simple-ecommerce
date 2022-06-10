<template>
  <div class="my-5">
    <b-container>
      <b-row v-if="this.groups.length > 0" class="mb-3">
        <b-col>
          <b-row>
        <b-col cols="1">
          <input type="checkbox" id="allCheckBox" v-model="allChecked" @click="checkAll($event.target.checked)" />
        </b-col>
            <b-col class="text-left"
              ><label for="allCheckBox"><b>전체선택</b></label></b-col
            >
            <b-col cols="2"
              ><b-btn variant="success" @click="discardCheckedItems()">선택삭제 <b-icon icon="cart-x-fill"></b-icon></b-btn
            ></b-col>
          </b-row>
        </b-col>
      </b-row>

      <b-row v-for="(deliveryGroup, group) in cartItems" :key="group" class="groupRow">
        <b-col>
          <b-row style="border-bottom: solid green">
            <b-col cols="1"><input type="checkbox" :id="'groupCheckBox_' + group" v-model="groupChecked[group]" @click="checkGroupAll($event.target.checked, group)" /></b-col>
            <b-col cols="8"
              ><h4 class="text-left">
                <label :for="'groupCheckBox_' + group"
                  ><b>{{ group }}</b></label
                >
              </h4></b-col
            >
            <b-col></b-col>
          </b-row>

          <b-row v-for="(item, index) in deliveryGroup" :key="index" style="border-bottom: solid 0.05em grey">
            <b-col cols="1"><input type="checkbox" :value="item.name" v-model="item.selected" @change="checkItem($event.target.checked, item)" /></b-col>
            <b-col cols="1"><b-img :src="item.image" width="80px" height="80px" class="mt-2 mb-2"></b-img></b-col>
            <b-col cols="5" class="text-left ml-3">{{ item.name }}</b-col>
            <b-col cols="2" class="text-right">{{ (item.quantity * item.price) | money }} 원</b-col>
            <b-col>
              <b-button variant="warning" @click="decreaseQuantity(item)">-</b-button>
              <span class="pl-3 pr-3">{{ item.quantity }}</span>
              <b-button variant="warning" @click="increaseQuantity(item)">+</b-button>
              <b-button class="ml-2" variant="white" @click="discardItem(item)"><b-icon icon="trash-fill" variant="danger"></b-icon></b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row v-if="this.groups.length > 0" class="buyRow">
        <b-col cols="2" class="text-right"> 결제예정금액 </b-col>
        <b-col cols="2" class="text-left" style="font-size: 2em; color: orange">
          <span v-if="checkedItemCnt > 0"
            ><b>{{ chekcedAmountOfPayment | money }}원</b></span
          >
          <span v-else
            ><b>{{ totalAmountOfPayment | money }}원</b></span
          >
        </b-col>
        <b-col cols="2">
          <b-button variant="warning" @click="order" class="mr-4"> 구매하기 <b-icon icon="credit-card"></b-icon></b-button>
        </b-col>
      </b-row>
      <b-row v-else> <p>장바구니가 비어있습니다.</p> </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

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
    this.getCartItems().then(() => {
      for (let g in this.groups) {
        let groupName = this.groups[g];
        for (let i in this.cartItems[groupName]) {
          let item = this.cartItems[groupName][i];
          this.totalAmountOfPayment += item.quantity * item.price;
          if (!item.selected) {
            this.groupChecked[groupName] = false;
          } else {
            this.chekcedAmountOfPayment += item.quantity * item.price;
            this.checkedItemCnt++;
            this.groupChecked[groupName] = true;
          }
        }
        if (this.groupChecked[groupName]) this.checkedGroupCnt++;
      }
    });
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems", "updateQuantity", "deleteCartItem", "changeSelectedItem"]),
    decreaseQuantity(item) {
      if (item.quantity === 1) return;
      if (item.selected) this.chekcedAmountOfPayment -= item.price;
      this.totalAmountOfPayment -= item.price;
      this.updateQuantity({ productId: item.productId, quantity: item.quantity - 1 });
    },
    async increaseQuantity(item) {
      let success = await this.updateQuantity({ productId: item.productId, quantity: item.quantity + 1 });
      if (success) {
      if (item.selected) this.chekcedAmountOfPayment += item.price;
      this.totalAmountOfPayment += item.price;
      }
    },
    checkAll(checked) {
      for (let g in this.groups) {
        this.checkGroupAll(checked, this.groups[g]);
      }
    },
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
    async discardCheckedItems() {
      let deleted = [];
      for (let g in this.groups) {
        let groupName = this.groups[g];
        for (let i in this.cartItems[groupName]) {
          if (this.cartItems[groupName][i].selected) {
            deleted.push(this.cartItems[groupName][i]);
          }
        }
      }
      if (deleted.length == 0) {
        alert("선택된 상품이 없습니다.");
        return;
      }
      for (let i in deleted) {
        await this.discardItem(deleted[i]);
      }
    },
    async buy() {
      if (this.checkedItemCnt == 0) {
        let amountOfPayment = this.totalAmountOfPayment;
        for (let g in this.groups) {
          let groupName = this.groups[g];
          for (let i in this.cartItems[groupName]) {
            this.discardItem(this.cartItems[groupName][i]);
          }
          alert("구매 성공! (금액 : " + amountOfPayment + " 원)");
        }
      } else {
        let amountOfPayment = this.chekcedAmountOfPayment;
        await this.discardCheckedItems();
        alert("구매 성공! (금액 : " + amountOfPayment + " 원)");
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
