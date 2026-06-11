package com.bottelx.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;
import com.bottelx.dto.QRGenerationResponse;

public interface QRCodeService {

       QRGenerationResponse  generateQRCode(
                        QRCodeRequest request,
                        UUID companyId);

        Page<QRCodeResponse> getAll(UUID companyId, Pageable pageable);

        QRCodeResponse scanQRCode(
                        String qrCode);

        void toggleStatus(
                        UUID companyId,
                        String id,
                        boolean active);

        void deleteQRCode(
                        UUID companyId,
                        String id);
}
