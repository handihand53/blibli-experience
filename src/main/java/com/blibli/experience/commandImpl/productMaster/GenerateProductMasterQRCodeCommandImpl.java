package com.blibli.experience.commandImpl.productMaster;

import com.blibli.experience.command.productMaster.GenerateProductMasterQRCodeCommand;
import com.blibli.experience.entity.document.ProductMaster;
import com.blibli.experience.repository.ProductMasterRepository;
import com.blibli.experience.util.QrGeneratorUtil;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class GenerateProductMasterQRCodeCommandImpl implements GenerateProductMasterQRCodeCommand {

    private ProductMasterRepository productMasterRepository;
    private QrGeneratorUtil qrGeneratorUtil;

    @Autowired
    public GenerateProductMasterQRCodeCommandImpl(ProductMasterRepository productMasterRepository, QrGeneratorUtil qrGeneratorUtil) {
        this.productMasterRepository = productMasterRepository;
        this.qrGeneratorUtil = qrGeneratorUtil;
    }

    @Override
    public Mono<String> execute(Integer request) {
        return productMasterRepository.findAll()
                .map(productMaster -> {
                    try {
                        return generateQRCode(productMaster);
                    } catch (IOException | WriterException e) {
                        e.printStackTrace();
                    }
                    return productMaster;
                })
                .flatMap(productMaster -> productMasterRepository.save(productMaster))
                .last()
                .map(productMaster -> toResponse());
    }

    private ProductMaster generateQRCode(ProductMaster productMaster) throws IOException, WriterException {
        String qrPath = qrGeneratorUtil.generateQR(productMaster.getProductId().toString());
        productMaster.setQrImagePath(qrPath);
        return productMaster;
    }

    private String toResponse() {
        return "Success";
    }
}
