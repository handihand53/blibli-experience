package com.blibli.experience.enums;

public enum  BarterItemStatus {

    IN_OWNER("In Owner"),
    SENT_TO_WAREHOUSE("Sent to Warehouse"),
    VERIFIED_IN_WAREHOUSE("Verified in Warehouse"),
    SENT_TO_CONSUMERS("Sent to Consumers"),
    IN_CONSUMERS("In Consumers");

    private String BarterItemStatus;

    BarterItemStatus(String barterItemStatus) {
        BarterItemStatus = barterItemStatus;
    }

    public String getBarterItemStatus() {
        return BarterItemStatus;
    }
}
