import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/product/list",
  },
  {
    path: "/product",
    name: "product",
    component: () => import("@/views/ProductView.vue"),
    redirect: "/product/list",
    children: [
      {
        path: "list",
        name: "productList",
        component: () => import("@/components/ProductList.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
