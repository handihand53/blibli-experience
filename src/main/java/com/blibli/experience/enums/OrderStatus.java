package com.blibli.experience.enums;

public enum OrderStatus {

    WAITING_FOR_PAYMENT("Waiting for Payment"),
    PAID("Paid"),
    DELIVERED_TO_CONSUMER("Delivered to Consumer"),
    FINISHED("Finished");

    private String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
