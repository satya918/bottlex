package com.bottelx.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.VerifyQRRequest;
import com.bottelx.dto.VerifyQRResponse;
import com.bottelx.services.VerificationService;

@RestController
@RequestMapping("/api/public/verify")
@CrossOrigin("*")
public class VerificationController {
    @Autowired
    private VerificationService verificationService;

    @PostMapping
    public VerifyQRResponse verify(
            @RequestBody VerifyQRRequest request,
            HttpServletRequest httpRequest) {

        return verificationService.verifyQRCode(
                request,
                httpRequest);
    }
}
