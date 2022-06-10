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

async function updateQuantity(productId, quantity, fail) {
  await api.put(prefix + `/${productId}/${quantity}`).catch(fail);
}

async function plus(productId, fail) {
  await api.put(prefix + `/${productId}/plus`).catch(fail);
}

async function minus(productId, fail) {
  await api.put(prefix + `/${productId}/minus`).catch(fail);
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

async function payAll(success, fail) {
  api
    .get(prefix + `/pay`)
    .then(success)
    .catch(fail);
}

async function paySelected(success, fail) {
  await api
    .get(prefix + `/pay/selected`)
    .then(success)
    .catch(fail);
}

export { insert, selectAll, updateQuantity, plus, minus, deleteItem, toggleSelect, payAll, paySelected };
