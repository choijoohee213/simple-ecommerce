import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

import productStore from "./modules/productStore";

export default new Vuex.Store({
  modules: {
    productStore,
  },
  state: {},
  getters: {},
  mutations: {},
  actions: {},
});
