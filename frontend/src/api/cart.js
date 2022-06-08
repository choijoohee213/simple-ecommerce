import { apiInstance } from "./index.js";

const api = apiInstance();
const prefix = "/cart";

function insert(productId, success, fail) {
  api
    .post(prefix + `/${productId}`)
    .then(success)
    .catch(fail);
}

async function selectAll(success, fail) {
  await api.get(prefix).then(success).catch(fail);
}

async function update(info, success, fail) {
  await api
    .put(prefix + `/${info.productId}/${info.quantity}`)
    .then(success)
    .catch(fail);
}

async function deleteItem(productId, success, fail) {
  await api
    .delete(prefix + `/${productId}`)
    .then(success)
    .catch(fail);
}

async function toggleSelect(productId, success, fail) {
  await api
    .put(prefix + `/${productId}/selected`)
    .then(success)
    .catch(fail);
}

export { insert, selectAll, update, deleteItem, toggleSelect };
