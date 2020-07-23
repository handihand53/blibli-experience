package com.blibli.experience.enums;

public enum BarterRoleEnum {

    SELLER("Seller"),
    BUYER("Buyer");

    private String receiptEnum;

    BarterRoleEnum(String receiptEnum) {
        this.receiptEnum = receiptEnum;
    }

    public String getReceiptEnum() {
        return receiptEnum;
    }
}
