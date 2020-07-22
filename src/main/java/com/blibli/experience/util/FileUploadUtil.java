package com.blibli.experience.util;

import com.blibli.experience.enums.UploadEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadUtil {

    private String temp = "Project/blibli-experience/src/main/resources/";
    private String projectDir = "D:/" + temp;
    private String pathServer = "http://localhost:8080/uploads/" + temp;
    private String uploadDir = "uploads/";

    public String uploadPhoto(@RequestParam MultipartFile multipartFile,
                              @RequestParam UUID id,
                              @RequestParam UploadEnum uploadEnum,
                              @RequestParam Integer count) throws IOException {
        String photoLink = projectDir + uploadDir + uploadEnum + id + "_" + count;
        File file = new File(photoLink);
        if(!file.exists()) {
            file.mkdirs();
        } else {
            file.delete();
        }
        multipartFile.transferTo(file);
        return photoLink;
    }

    public Boolean validatePhoto(MultipartFile multipartFile) throws IOException {
        return multipartFile.getContentType().contains("image/");
    }

}
