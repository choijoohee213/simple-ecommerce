<template>
  <div class="my-5">
    <b-container class="bv-example-row">
      <b-row v-for="(item, index) in cartItems" :key="index">
        <b-col>{{ item.name }}</b-col>
        <b-col>{{ item.quantity * item.price }}</b-col>
        <b-col>
          <b-button variant="warning" @click="decreaseQuantity(item.productId, item.quantity)">-</b-button>
          <span class="p-3">{{ item.quantity }}</span>
          <b-button variant="warning" @click="updateQuantity({ productId: item.productId, quantity: item.quantity + 1 })">+</b-button>
        </b-col>
      </b-row>
      <b-row v-if="!cartItems.length"> <p>장바구니가 비어있습니다.</p> </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const cartStore = "cartStore";

export default {
  name: "CartList",
  computed: {
    ...mapState(cartStore, ["cartItems"]),
  },
  created() {
    this.getCartItems();
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems", "updateQuantity"]),
    decreaseQuantity(productId, quantity) {
      if (quantity === 1) return;
      this.updateQuantity({ productId: productId, quantity: quantity - 1 });
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
