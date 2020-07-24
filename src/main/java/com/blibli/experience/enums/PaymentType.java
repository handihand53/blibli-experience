package com.blibli.experience.enums;

public enum PaymentType {

    INDOMART("Indomart"),
    ALFAMART("Alfamart"),
    POS_INDONESIA("Pos Indonesia"),
    BANK_TRANSFER_MANDIRI("Bank Transfer Mandiri"),
    BANK_TRANSFER_BNI("Bank Transfer BNI"),
    BANK_TRANSFER_BCA("Bank Transfer BCA"),
    GOPAY("Gopay"),
    OVO("Ovo"),
    LINK_AJA("Link Aja");

    private String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
