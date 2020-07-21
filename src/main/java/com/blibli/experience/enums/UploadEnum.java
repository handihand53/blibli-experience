package com.blibli.experience.enums;

public enum UploadEnum {

    PRODUCT_PHOTO("productPhoto"),
    BARTER_PRODUCT_PHOTO("barterProductPhoto");

    private String uploadEnum;

    UploadEnum(String uploadEnum) {
        this.uploadEnum = uploadEnum;
    }

    public String getUploadEnum() {
        return uploadEnum;
    }
}
