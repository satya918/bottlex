import apiClient from "../api/apiClient";

export const QRAPI = {

    getAll: () =>
        apiClient.get("/api/admin/qr"),

    generate: (data: any) =>
        apiClient.post(
            "/api/admin/qr/generate",
            data
        ),

    scan: (code: string) =>
        apiClient.post(
            `/api/admin/qr/scan/${code}`
        ),

    toggleStatus: (
        id: string,
        active: boolean
    ) =>
        apiClient.patch(
            `/api/admin/qr/${id}/status?active=${active}`
        ),

    delete: (id: string) =>
        apiClient.delete(
            `/api/admin/qr/${id}`
        ),
};