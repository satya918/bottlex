package com.bottelx.services.serviceImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottelx.dto.QRCodeRequest;
import com.bottelx.dto.QRCodeResponse;
import com.bottelx.entity.Batch;
import com.bottelx.entity.Product;
import com.bottelx.entity.QRCode;
import com.bottelx.repository.BatchRepository;
import com.bottelx.repository.ProductRepository;
import com.bottelx.repository.QRCodeRepository;
import com.bottelx.services.QRCodeService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class QRCodeServiceImpl
        implements QRCodeService {
@Autowired
    private  QRCodeRepository qrCodeRepository;
@Autowired

    private  ProductRepository productRepository;
@Autowired

    private  BatchRepository batchRepository;

    public QRCodeServiceImpl(
            QRCodeRepository qrCodeRepository,
            ProductRepository productRepository,
            BatchRepository batchRepository
    ) {
        this.qrCodeRepository = qrCodeRepository;
        this.productRepository = productRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public QRCodeResponse generateQRCode(
            QRCodeRequest request
    ) {

        Product product =
                productRepository
                        .findById(request.getProductId())
                        .orElseThrow();

        Batch batch =
                batchRepository
                        .findById(request.getBatchId())
                        .orElseThrow();

        QRCode qr = new QRCode();

        qr.setQrCode(
                UUID.randomUUID().toString()
        );

        qr.setQrType(
                request.getQrType()
        );

        qr.setProduct(product);

        qr.setBatch(batch);

        QRCode saved =
                qrCodeRepository.save(qr);

        return map(saved);
    }

    @Override
    public List<QRCodeResponse> getAll() {

        return qrCodeRepository
                .findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public QRCodeResponse scanQRCode(
            String qrCode
    ) {

        QRCode qr =
                qrCodeRepository
                        .findByQrCode(qrCode)
                        .orElseThrow();

        qr.setLastScannedAt(
                LocalDateTime.now()
        );

        return map(
                qrCodeRepository.save(qr)
        );
    }

    @Override
    public void toggleStatus(
            String id,
            boolean active
    ) {

        QRCode qr =
                qrCodeRepository
                        .findById(id)
                        .orElseThrow();

        qr.setActive(active);

        qrCodeRepository.save(qr);
    }

    @Override
    public void deleteQRCode(
            String id
    ) {

        qrCodeRepository.deleteById(id);
    }

    private QRCodeResponse map(
            QRCode qr
    ) {

        return new QRCodeResponse(
                qr.getId(),
                qr.getQrCode(),
                qr.getQrType(),
                qr.isActive(),
                qr.getProduct().getProductName(),
                qr.getBatch().getBatchNumber(),
                qr.getCreatedAt(),
                qr.getLastScannedAt()
        );
    }
}
