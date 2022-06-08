<template>
  <div class="my-5">
    <b-container class="bv-example-row">
      <b-row class="m-3" v-if="this.groups.length > 0">
        <b-col cols="1">
          <input type="checkbox" v-model="allChecked" @click="checkAll($event.target.checked)" />
        </b-col>
        <b-col class="text-left">전체선택</b-col>
      </b-row>

      <b-row v-for="(deliveryGroup, group) in cartItems" :key="group">
        <b-col>
          <b-row class="m-3">
            <b-col cols="1"><input type="checkbox" v-model="groupChecked[group]" @click="checkGroupAll($event.target.checked, group)" /></b-col>
            <b-col cols="9"
              ><h4 class="float-left">{{ group }}</h4></b-col
            >
            <b-col></b-col>
          </b-row>

          <b-row class="m-3" v-for="(item, index) in deliveryGroup" :key="index">
            <b-col cols="1"><input type="checkbox" :value="item.name" v-model="item.selected" @change="checkItem($event.target.checked, group, item.productId)" /></b-col>
            <b-col class="text-left">{{ item.name }}</b-col>
            <b-col>{{ item.quantity * item.price }}</b-col>
            <b-col>
              <b-button variant="warning" @click="decreaseQuantity(item.productId, item.quantity)">-</b-button>
              <span class="p-3">{{ item.quantity }}</span>
              <b-button variant="warning" @click="updateQuantity({ productId: item.productId, quantity: item.quantity + 1 })">+</b-button>
              <b-button class="ml-3" variant="white" @click="discardItem(item.productId, group)"><b-icon icon="trash-fill" variant="danger"></b-icon></b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row v-if="this.groups.length === 0"> <p>장바구니가 비어있습니다.</p> </b-row>
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
      checkedGroupCnt: 0,
      groupChecked: {},
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
          if (!this.cartItems[groupName][i].selected) {
            this.groupChecked[groupName] = false;
            this.checkedGroupCnt--;
            break;
          } else {
            this.groupChecked[groupName] = true;
          }
        }
        this.checkedGroupCnt++;
      }
    });
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems", "updateQuantity", "deleteCartItem", "changeSelectedItem"]),
    decreaseQuantity(productId, quantity) {
      if (quantity === 1) return;
      this.updateQuantity({ productId: productId, quantity: quantity - 1 });
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
        if (this.cartItems[groupName][i].selected != checked) {
          this.changeSelectedItem(this.cartItems[groupName][i].productId);
        }
      }
    },
    checkItem(checked, groupName, productId) {
      this.changeSelectedItem(productId);
      if (!checked) {
        if (this.groupChecked[groupName]) {
          this.checkedGroupCnt--;
          this.groupChecked[groupName] = false;
        }
        return;
      }
      for (let i in this.cartItems[groupName]) {
        if (!this.cartItems[groupName][i].selected) {
          return;
        }
      }
      this.checkedGroupCnt++;
      this.groupChecked[groupName] = true;
    },
    async discardItem(productId, groupName) {
      let isChecked = this.groupChecked[groupName];
      await this.deleteCartItem(productId).then(() => {
        if (!this.cartItems[groupName]) {
          if (isChecked) this.checkedGroupCnt--;
          return;
        }
        for (let i in this.cartItems[groupName]) {
          if (!this.cartItems[groupName][i].selected) {
            return;
          }
        }
        if (!isChecked) {
          this.checkedGroupCnt++;
          this.groupChecked[groupName] = true;
        }
      });
    },
  },
};
</script>

<style scoped>
.row {
  justify-content: center;
  margin-bottom: 10%;
}
</style>
