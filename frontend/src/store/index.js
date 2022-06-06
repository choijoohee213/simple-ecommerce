import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import productStore from "./modules/productStore";
import cartStore from "./modules/cartStore";

export default new Vuex.Store({
  modules: {
    productStore,
    cartStore,
  },
  state: {},
  getters: {},
  mutations: {},
  actions: {},
});
