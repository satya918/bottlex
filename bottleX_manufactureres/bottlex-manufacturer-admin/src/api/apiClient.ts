import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/",
  withCredentials: true,
  headers: {
    "Content-Type": "application/json",
  },
});

// REQUEST INTERCEPTOR

apiClient.interceptors.request.use(
  (config) => {

    const token =
      localStorage.getItem("manufacturer_token");

    if (token) {

      config.headers.Authorization =
        `Bearer ${token}`;
    }

    return config;
  },

  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;