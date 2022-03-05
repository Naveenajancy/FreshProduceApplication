package com.freshproduceapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class TrackOrder {
    private int trackOrderId;
    private UUID uuid;
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
