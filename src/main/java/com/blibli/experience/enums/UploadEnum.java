package com.blibli.experience.enums;

public enum UploadEnum {

    productPhoto("productPhoto"),
    barterProductPhoto("barterProductPhoto"),
    barterSubmissionPhoto("barterSubmissionPhoto");

    private String uploadEnum;

    UploadEnum(String uploadEnum) {
        this.uploadEnum = uploadEnum;
    }

    public String getUploadEnum() {
        return uploadEnum;
    }
}
