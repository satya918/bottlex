import apiClient from "../api/apiClient";

export const ProductsAPI = {

    getProducts: (
        page: number,
        size: number,
        search: string
    ) =>
        apiClient.get(
            `/api/products?page=${page}&size=${size}&search=${search}`
        ),

    createProduct: (data: any) =>
        apiClient.post("/api/products", data),

    updateProduct: (id: string, data: any) =>
        apiClient.put(`/api/products/${id}`, data),

    deleteProduct: (id: string) =>
        apiClient.delete(`/api/products/${id}`),

    toggleStatus: (
        id: string,
        active: boolean
    ) =>
        apiClient.patch(
            `/api/products/${id}/status?active=${active}`
        ),

    getCategories: () =>
        apiClient.get("/api/admin/categories"),
};