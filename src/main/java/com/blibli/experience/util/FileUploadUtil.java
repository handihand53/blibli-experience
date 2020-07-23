package com.blibli.experience.util;

import com.blibli.experience.enums.UploadEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadUtil {

    private String temp = "blibli-experience/src/main/resources/";
    private String projectDir = "D:/Projects/" + temp;
    private String pathServer = "http://192.168.43.138:8080/";
    private String uploadDir = "uploads/";

    public List<String> uploadAllPhoto(List<MultipartFile> photos,
                                       UUID productId,
                                       UploadEnum uploadEnum) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            imagePaths.add(uploadPhoto(photos.get(i), productId, uploadEnum, i));
        }
        return imagePaths;
    }

    public String uploadPhoto(MultipartFile photo,
                              UUID productId,
                              UploadEnum uploadEnum,
                              Integer count) throws IOException {
        String photoLink = projectDir + uploadDir + uploadEnum + "/" + productId + "_" + count + ".jpg";
        File file = new File(photoLink);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            file.delete();
        }
        photo.transferTo(file);
        return photoLink;
    }

    public Boolean validatePhoto(MultipartFile multipartFile) throws IOException {
        return multipartFile.getContentType().contains("image/");
    }

}
