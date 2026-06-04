package com.bottelx.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;
import com.bottelx.services.QRCodeService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/qr")
@CrossOrigin("*")
public class QRCodeController {
@Autowired
    private  QRCodeService qrCodeService;

    public QRCodeController(
            QRCodeService qrCodeService
    ) {
        this.qrCodeService = qrCodeService;
    }

    @PostMapping("/generate")
    public QRCodeResponse generate(
            @RequestBody QRCodeRequest request
    ) {

        return qrCodeService
                .generateQRCode(request);
    }

    @GetMapping
    public List<QRCodeResponse> getAll() {

        return qrCodeService.getAll();
    }

    @PostMapping("/scan/{code}")
    public QRCodeResponse scan(
            @PathVariable String code
    ) {

        return qrCodeService
                .scanQRCode(code);
    }

    @PatchMapping("/{id}/status")
    public void toggleStatus(
            @PathVariable String id,
            @RequestParam boolean active
    ) {

        qrCodeService.toggleStatus(
                id,
                active
        );
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {

        qrCodeService.deleteQRCode(id);
    }
}
