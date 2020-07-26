package com.blibli.experience.enums;

public enum  ProductBiddingCondition {

    NEW("New"),
    SECOND("Second");

    private String productBiddingCondition;

    ProductBiddingCondition(String productBiddingCondition) {
        this.productBiddingCondition = productBiddingCondition;
    }

    public String getProductBiddingCondition() {
        return productBiddingCondition;
    }
}
