import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:8080/ecommerce/",
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance };
