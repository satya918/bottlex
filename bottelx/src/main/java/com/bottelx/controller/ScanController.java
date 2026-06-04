package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.QrScanRequest;
import com.bottelx.services.ScanService;

@RestController
@RequestMapping("/scan")
@CrossOrigin("*")
public class ScanController {
    @Autowired
    private  ScanService scanService;

    public ScanController(
            ScanService scanService
    ) {

        this.scanService = scanService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyQr(
            @RequestBody QrScanRequest request, Authentication authentication
    ) {

        String response =
                scanService.verifyQr(request, authentication);

        return ResponseEntity.ok(response);
    }
}
