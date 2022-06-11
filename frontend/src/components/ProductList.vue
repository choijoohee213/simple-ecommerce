<template>
  <div class="my-3">
    <b-container class="bv-example-row">
      <b-row v-for="(product, index) in products" :key="index" style="border-bottom: solid 0.05em #cfcfcf">
        <b-col><b-img :src="product.image" width="110px" height="110px"></b-img></b-col>
        <b-col v-if="product.quantity > 0" cols="4" class="text-left pl-3">
          <b>{{ product.name }}</b>
        </b-col>
        <b-col v-else cols="4" class="text-left pl-3" style="text-decoration: line-through red">
          <b>{{ product.name }}</b>
        </b-col>
        <b-col cols="2">
          <span style="font-weight: bold">{{ product.price | money }}</span> 원
        </b-col>
        <b-col>{{ product.deliveryGroup }}</b-col>
        <b-col>
          <b-button variant="success" @click="addCartItem(`${product.id}`)">장바구니 <b-icon icon="cart-plus-fill"></b-icon></b-button>
        </b-col>
      </b-row>
      <b-row v-if="!products.length"> <p>상품이 없습니다.</p> </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { insert } from "@/api/cart.js";

const productStore = "productStore";

export default {
  name: "ProductList",
  data() {
    return {
      list: null,
    };
  },
  computed: {
    ...mapState(productStore, ["products"]),
  },
  created() {
    this.getProducts();
  },
  methods: {
    ...mapActions(productStore, ["getProducts"]),
    //상품 목록 중 원하는 상품을 장바구니에 추가
    addCartItem(productId) {
      insert(
        productId,
        (response) => {
          alert(response.data.message);
        },
        (error) => {
          console.log(error);
          alert(error.response.data);
        }
      );
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
  justify-content: center;
  padding-bottom: 1%;
  padding-top: 1%;
}

.row:hover {
  background-color: #f5f5f5;
}
</style>
