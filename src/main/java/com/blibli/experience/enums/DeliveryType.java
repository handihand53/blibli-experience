package com.blibli.experience.enums;

public enum DeliveryType {

    DELIVERY_TO_HOME("Delivery to Home"),
    SELF_SERVICE("Self Service");

    private String deliveryType;

    DeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }
}
