<template>
  <div class="my-5">
    <b-container class="bv-example-row">
      <b-row v-for="(products, index) in groupProducts" :key="index">
        <b-col v-for="(product, index) in products" :key="index">
          <b-row class="m-0"
            ><b>{{ product.name }}</b></b-row
          >
          <b-row class="m-0">{{ product.price }}</b-row>
          <b-row class="m-0">{{ product.quantity }}</b-row>
          <b-row class="m-0">{{ product.deliveryGroup }}</b-row>
          <b-row class="m-0"> <b-button variant="success" @click="addCartItem(`${product.id}`)">장바구니</b-button></b-row>
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
    groupProducts() {
      let size = 4;
      let result = [];

      size = parseInt(size) || 2;

      for (let x = 0; x < Math.ceil(this.products.length / size); x++) {
        let start = x * size;
        let end = start + size;

        result.push(this.products.slice(start, end));
      }
      return result;
    },
  },
  created() {
    this.getProducts();
  },
  methods: {
    ...mapActions(productStore, ["getProducts"]),
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
};
</script>

<style scoped>
.row {
  justify-content: center;
  margin-bottom: 10%;
}
</style>
