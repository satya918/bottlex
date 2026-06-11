import apiClient from "../api/apiClient";

const getCompanyId = () => {
    const user = localStorage.getItem("user");

    return user
        ? JSON.parse(user).company.id
        : "";
};

export const CategoriesAPI = {
   

    createCategory: (data: any) =>
        apiClient.post(
            `/api/admin/categories/${getCompanyId()}`,
            data
        ),

    updateCategory: (
        id: string,
        data: any
    ) =>
        apiClient.put(
            `/api/admin/categories/${getCompanyId()}/${id}`,
            data
        ),

    deleteCategory: (id: string) =>
        apiClient.delete(
            `/api/admin/categories/${getCompanyId()}/${id}`
        ),

    toggleStatus: (
        id: string,
        active: boolean
    ) =>
        apiClient.patch(
            `/api/admin/categories/${getCompanyId()}/${id}/status?active=${active}`
        ),

    getCategories: () => apiClient.get(`/api/admin/categories/${getCompanyId()}`),
};