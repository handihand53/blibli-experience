package com.blibli.experience.enums;

public enum ProductBarterCondition {

    NEW("New"),
    SECOND("Second");

    private String ProductBarterCondition;

    ProductBarterCondition(String productBarterCondition) {
        ProductBarterCondition = productBarterCondition;
    }

    public String getProductBarterCondition() {
        return ProductBarterCondition;
    }
}
