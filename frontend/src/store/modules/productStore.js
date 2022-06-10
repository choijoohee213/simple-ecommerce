import { selectAll } from "@/api/product.js";

const productStore = {
  namespaced: true,
  state: {
    products: [],
  },
  getters: {},
  mutations: {
    SET_PRODUCTS: function (state, products) {
      state.products = products;
    },
  },
  actions: {
    // 전체 상품 목록 가져오기
    getProducts({ commit }) {
      selectAll((response) => commit("SET_PRODUCTS", response.data));
    },
  },
};

export default productStore;
