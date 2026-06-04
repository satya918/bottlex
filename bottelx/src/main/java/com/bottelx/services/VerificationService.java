package com.bottelx.services;

 

import com.bottelx.dto.VerifyQRRequest;
import com.bottelx.dto.VerifyQRResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface VerificationService {

    VerifyQRResponse verifyQRCode(
            VerifyQRRequest request,
            HttpServletRequest httpRequest
    );
}
