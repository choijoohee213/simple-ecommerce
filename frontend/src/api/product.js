import { apiInstance } from "./index.js";

const api = apiInstance();

function selectAll(success, fail) {
  api.get(`/product`).then(success).catch(fail);
}

export { selectAll };
