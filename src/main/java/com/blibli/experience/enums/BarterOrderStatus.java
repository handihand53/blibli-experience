package com.blibli.experience.enums;

public enum BarterOrderStatus {

    WAITING_IN_WAREHOUSE("Waiting in Warehouse"),
    SENT_TO_WAREHOUSE("Sent to Warehouse"),
    VERIFIED_IN_WAREHOUSE("Verified In Warehouse"),
    SENT_TO_CONSUMERS("Sent To Consumers"),
    FINISHED("Finished");

    private String BarterOrderStatus;

    BarterOrderStatus(String barterOrderStatus) {
        BarterOrderStatus = barterOrderStatus;
    }

    public String getBarterOrderStatus() {
        return BarterOrderStatus;
    }
}
