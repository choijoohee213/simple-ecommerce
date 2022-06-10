import { selectAll, plus, minus, deleteItem, toggleSelect, paySelected } from "@/api/cart.js";

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
      await selectAll((response) => commit("SET_CART_ITEMS", response.data));
    },
    async plusQuantity({ commit }, productId) {
      let success = true;
      await plus(productId, (error) => {
        alert(error.response.data);
        success = false;
      });
      if (!success) return success;
      selectAll((response) => commit("SET_CART_ITEMS", response.data));
      return success;
    },
    async minusQuantity({ commit }, productId) {
      await minus(productId, (error) => alert(error.response.data));
      selectAll((response) => commit("SET_CART_ITEMS", response.data));
    },
    async deleteCartItem({ commit }, productId) {
      await deleteItem(productId);
      await selectAll((response) => commit("SET_CART_ITEMS", response.data));
    },
    async changeSelectedItem({ commit }, productId) {
      await toggleSelect(productId);
      selectAll((response) => commit("SET_CART_ITEMS", response.data));
    },
    async paySelectedItems({ commit }, amountOfPayment) {
      await paySelected(
        () => alert("구매 성공! (금액 : " + amountOfPayment + " 원)"),
        (error) => alert(error.response.data)
      );
      await selectAll((response) => commit("SET_CART_ITEMS", response.data));
    },
  },
};

export default cartStore;
