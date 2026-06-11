import apiClient from "../api/apiClient";

const getCompanyId = () => {
    const user = localStorage.getItem("user");

    return user
        ? JSON.parse(user).company.id
        : "";
};

export const BatchesAPI = {
   

    createBatch: (data: any) =>
        apiClient.post(
            `/api/admin/batches/${getCompanyId()}`,
            data
        ),

    updateBatch: (
        id: string,
        data: any
    ) =>
        apiClient.put(
            `/api/admin/batches/${getCompanyId()}/${id}`,
            data
        ),

    deleteBatch : (id: string) =>
        apiClient.delete(
            `/api/admin/batches/${getCompanyId()}/${id}`
        ),

    toggleStatus: (
        id: string,
        active: boolean
    ) =>
        apiClient.patch(
            `/api/admin/batches/${getCompanyId()}/${id}/status?active=${active}`
        ),

    getBatches: () => apiClient.get(`/api/admin/batches/${getCompanyId()}`),
};