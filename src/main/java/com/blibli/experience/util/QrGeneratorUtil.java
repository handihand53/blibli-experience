package com.blibli.experience.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Slf4j
@Service
public class QrGeneratorUtil {

    private static final Integer WIDTH = 400;
    private static final Integer HEIGHT = 400;

    private String temp = "blibli-experience/src/main/resources/";
    private String projectDir = "D:/Projects/" + temp;
    private String pathServer = "http://192.168.43.138:8080/";
    private String uploadDir = "uploads/productQR/";

    public String generateQR(String productId) {
        try {
            String qrLink = projectDir + uploadDir + productId + ".png";
            File file = new File(qrLink);
            if(!file.exists()) {
                file.mkdirs();
            }else {
                file.delete();
            }
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(productId, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            Path path = FileSystems.getDefault().getPath(qrLink);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            return qrLink;
        } catch (IOException | WriterException ex) {
            return ex.getMessage();
        }
    }

}
