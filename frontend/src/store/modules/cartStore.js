import { selectAll, update, deleteItem, toggleSelect } from "@/api/cart.js";

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
    async getCartItems({ commit }) {
      await selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
    async updateQuantity({ commit }, info) {
      await update(info);
      selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
    async deleteCartItem({ commit }, productId) {
      await deleteItem(productId);
      selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
    async changeSelectedItem({ commit }, productId) {
      await toggleSelect(productId);
      selectAll((response) => {
        commit("SET_CART_ITEMS", response.data);
      });
    },
  },
};

export default cartStore;
