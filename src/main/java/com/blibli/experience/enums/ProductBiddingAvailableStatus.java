package com.blibli.experience.enums;

public enum ProductBiddingAvailableStatus {

    AVAILABLE("Available"),
    FINISHED("Finished"),
    ORDER_GENERATED("Order Generated");

    private String productBiddingAvailableStatus;

    ProductBiddingAvailableStatus(String productBiddingAvailableStatus) {
        this.productBiddingAvailableStatus = productBiddingAvailableStatus;
    }

    public String getProductBiddingAvailableStatus() {
        return productBiddingAvailableStatus;
    }
}
