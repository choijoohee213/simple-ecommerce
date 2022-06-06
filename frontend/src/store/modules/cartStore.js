import { selectAll } from "@/api/cart.js";

const cartStore = {
  namespaced: true,
  state: {
    cartItems: null,
  },
  getters: {},
  mutations: {
    SET_CART_ITEMS: function (state, cartItems) {
      state.cartItems = cartItems;
    },
  },
  actions: {
    getCartItems({ commit }) {
      selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
  },
};

export default cartStore;
