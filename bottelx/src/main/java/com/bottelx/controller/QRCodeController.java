package com.bottelx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;
import com.bottelx.dto.QRGenerationResponse;
import com.bottelx.services.QRCodeService;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/qr")
public class QRCodeController {
        @Autowired
        private QRCodeService qrCodeService;

        @PostMapping("/generate/{companyId}")
        public QRGenerationResponse  generate(
                        @RequestBody QRCodeRequest request,
                        @PathVariable UUID companyId) {

                return qrCodeService
                                .generateQRCode(request, companyId);
        }

        @GetMapping("/getAll/{companyId}")
        public Page<QRCodeResponse> getAll(@PathVariable UUID companyId,  Pageable pageable) {

                return qrCodeService.getAll(companyId, pageable);
        }

        @PostMapping("/scan/{code}")
        public QRCodeResponse scan(
                        @PathVariable String code) {

                return qrCodeService
                                .scanQRCode(code);
        }

        @PatchMapping("/{companyId}/{id}/status")
        public void toggleStatus(
                        @PathVariable UUID companyId,
                        @PathVariable String id,
                        @RequestParam boolean active) {

                qrCodeService.toggleStatus(
                                companyId,
                                id,
                                active);
        }

        @DeleteMapping("/delete/{companyId}/{id}")
        public void delete(
                        @PathVariable UUID companyId,
                        @PathVariable String id) {

                qrCodeService.deleteQRCode(companyId, id);
        }
}
