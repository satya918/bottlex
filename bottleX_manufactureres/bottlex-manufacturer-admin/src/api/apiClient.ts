import axios from "axios";

const apiClient = axios.create({
  baseURL: "https://bottlex-1.onrender.com",
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