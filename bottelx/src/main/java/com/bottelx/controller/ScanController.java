package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.QrScanRequest;
import com.bottelx.services.ScanService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/scan")
public class ScanController {
    @Autowired
    private ScanService scanService;

    public ScanController(
            ScanService scanService) {

        this.scanService = scanService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyQr(
            @RequestBody QrScanRequest request, Authentication authentication, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        String host = httpRequest.getRemoteHost();
        String user = httpRequest.getRemoteUser();
        System.out.println("------------------------------------------------------------");
        System.out.println("--------------------IP: " + ip + ", Host: " + host + ", User: " + user);
        String response = scanService.verifyQr(request, authentication, ip);

        return ResponseEntity.ok(response);
    }
}
