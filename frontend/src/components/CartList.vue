<template>
  <div class="my-5">
    <b-container class="bv-example-row">
      <b-row v-for="(deliveryGroup, group) in cartItems" :key="group">
        <b-col>
          <b-row class="m-3">
            <b-col cols="1"><input type="checkbox" v-model="allChecked[group]" @click="checkedAll($event.target.checked, group)" /></b-col>
            <b-col cols="9"
              ><h4 class="float-left">{{ group }}</h4></b-col
            >
            <b-col></b-col>
          </b-row>

          <b-row class="m-3" v-for="(item, index) in deliveryGroup" :key="index">
            <b-col cols="1"><input type="checkbox" :value="item.name" v-model="item.selected" @change="selected(group, item.productId)" /></b-col>
            <b-col class="text-left">{{ item.name }}</b-col>
            <b-col>{{ item.quantity * item.price }}</b-col>
            <b-col>
              <b-button variant="warning" @click="decreaseQuantity(item.productId, item.quantity)">-</b-button>
              <span class="p-3">{{ item.quantity }}</span>
              <b-button variant="warning" @click="updateQuantity({ productId: item.productId, quantity: item.quantity + 1 })">+</b-button>
              <b-button class="ml-3" variant="white" @click="deleteCartItem(item.productId)"><b-icon icon="trash-fill" variant="danger"></b-icon></b-button>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row v-if="Object.keys(this.cartItems).length === 0"> <p>장바구니가 비어있습니다.</p> </b-row>
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
      allChecked: {
        쓱배송: false,
        새벽배송: false,
        택배: false,
      },
      groups: {},
    };
  },
  computed: {
    ...mapState(cartStore, ["cartItems"]),
  },
  created() {
    this.getCartItems()
      .then(() => {
        this.groups = Object.keys(this.cartItems);
      })
      .then(() => {
        for (let g in this.groups) {
          let groupName = this.groups[g];
          for (let i in this.cartItems[groupName]) {
            if (!this.cartItems[groupName][i].selected) {
              this.allChecked[groupName] = false;
              break;
            } else {
              this.allChecked[groupName] = true;
            }
          }
        }
      });
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems", "updateQuantity", "deleteCartItem", "changeSelectedItem"]),
    decreaseQuantity(productId, quantity) {
      if (quantity === 1) return;
      this.updateQuantity({ productId: productId, quantity: quantity - 1 });
    },
    checkedAll(checked, groupName) {
      this.allChecked[groupName] = checked;
      for (let i in this.cartItems[groupName]) {
        if (this.cartItems[groupName][i].selected != checked) {
          this.changeSelectedItem(this.cartItems[groupName][i].productId);
        }
      }
    },
    selected(groupName, productId) {
      this.changeSelectedItem(productId);
      for (let i in this.cartItems[groupName]) {
        if (!this.cartItems[groupName][i].selected) {
          this.allChecked[groupName] = false;
          return;
        } else {
          this.allChecked[groupName] = true;
        }
      }
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
