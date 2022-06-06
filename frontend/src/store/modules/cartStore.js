import { selectAll, update } from "@/api/cart.js";

const cartStore = {
  namespaced: true,
  state: {
    cartItems: [],
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
    async updateQuantity({ commit }, info) {
      await update(info);
      selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
  },
};

export default cartStore;
