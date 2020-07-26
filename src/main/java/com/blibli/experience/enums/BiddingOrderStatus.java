package com.blibli.experience.enums;

public enum BiddingOrderStatus {

    WAITING_FOR_PAYMENT_FROM_BIDDING_WINNER("Waiting for Payment From Winner"),
    PAID("Paid"),
    DELIVERED_TO_BIDDING_OWNER("Delivered to Barter Owner"),
    PAID_TO_WINNER("Paid to Winner"),
    FINISHED("Finished");

    private String biddingOrderStatus;

    BiddingOrderStatus(String biddingOrderStatus) {
        this.biddingOrderStatus = biddingOrderStatus;
    }

    public String getBiddingOrderStatus() {
        return biddingOrderStatus;
    }
}
