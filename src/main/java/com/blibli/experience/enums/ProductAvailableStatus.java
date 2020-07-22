package com.blibli.experience.enums;

public enum ProductAvailableStatus {

    AVAILABLE("Available"),
    NOT_AVAILABLE("Not Available"),
    SUSPENDED("Suspended");

    private String productAvailableStatus;

    ProductAvailableStatus(String productAvailableStatus) {
        this.productAvailableStatus = productAvailableStatus;
    }

    public String getProductAvailableStatus() {
        return productAvailableStatus;
    }
}
