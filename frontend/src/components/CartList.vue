<template>
  <div class="my-5">
    <b-container class="bv-example-row">
      <b-row v-for="item in cartItems" :key="item">
        <b-col>{{ item.name }}</b-col>
        <b-col>{{ item.price }}</b-col>
        <b-col>{{ item.quantity }}</b-col>
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
  data() {
    return {
      list: null,
    };
  },
  computed: {
    ...mapState(cartStore, ["cartItems"]),
  },
  created() {
    this.getCartItems();
  },
  methods: {
    ...mapActions(cartStore, ["getCartItems"]),
  },
};
</script>

<style scoped>
.row {
  justify-content: center;
  margin-bottom: 10%;
}
</style>