package com.blibli.experience.enums;

public enum BidStatus {

    WINNING("Winning"),
    LOSING("Losing");

    private String bidStatus;

    BidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    public String getBidStatus() {
        return bidStatus;
    }
}
