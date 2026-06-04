package com.bottelx.services;



import java.util.List;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;

public interface QRCodeService {

    QRCodeResponse generateQRCode(
            QRCodeRequest request
    );

    List<QRCodeResponse> getAll();

    QRCodeResponse scanQRCode(
            String qrCode
    );

    void toggleStatus(
            String id,
            boolean active
    );

    void deleteQRCode(
            String id
    );
}
