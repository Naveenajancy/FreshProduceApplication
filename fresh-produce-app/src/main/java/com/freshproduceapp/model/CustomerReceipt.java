package com.freshproduceapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomerReceipt {
    private int trackOrderId;
    private UUID uuid; //unique id for each customer
    private List<CustomerPurchaseItem> customerPurchaseItemList;
    private BigDecimal totalCost;
    private LocalDate datePurchased;

    public int getTrackOrderId() {
        return trackOrderId;
    }

    public void setTrackOrderId(int trackOrderId) {
        this.trackOrderId = trackOrderId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<CustomerPurchaseItem> getCustomerPurchaseItemList() {
        return customerPurchaseItemList;
    }

    public void setCustomerPurchaseItemList(List<CustomerPurchaseItem> customerPurchaseItemList) {
        this.customerPurchaseItemList = customerPurchaseItemList;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(LocalDate datePurchased) {
        this.datePurchased = datePurchased;
    }
}
