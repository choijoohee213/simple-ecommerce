import { apiInstance } from "./index.js";

const api = apiInstance();
const prefix = "/cart";

function insert(productId, success, fail) {
  api
    .post(prefix + `/${productId}`)
    .then(success)
    .catch(fail);
}

function selectAll(success, fail) {
  api.get(prefix).then(success).catch(fail);
}

async function update(info, success, fail) {
  await api
    .put(prefix + `/${info.productId}/${info.quantity}`)
    .then(success)
    .catch(fail);
}

export { insert, selectAll, update };
