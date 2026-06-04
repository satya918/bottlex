import apiClient from "../api/apiClient";

export const VerificationAPI = {

    verify: (qrCode: string) =>
        apiClient.post(
            "/api/public/verify",
            { qrCode }
        ),
};