package com.freshproduceapp.model;

import java.math.BigDecimal;
import java.util.UUID;

public class TrackProduct {
    private int trackProductId;
    private UUID customerUUID;
    private int freshProduceId;
    private double quantity;
    private BigDecimal pricePerPound;
    private BigDecimal quantityPrice;

    public int getTrackProductId() {
        return trackProductId;
    }

    public void setTrackProductId(int trackProductId) {
        this.trackProductId = trackProductId;
    }

    public UUID getCustomerUUID() {
        return customerUUID;
    }

    public void setCustomerUUID(UUID customerUUID) {
        this.customerUUID = customerUUID;
    }

    public int getFreshProduceId() {
        return freshProduceId;
    }

    public void setFreshProduceId(int freshProduceId) {
        this.freshProduceId = freshProduceId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerPound() {
        return pricePerPound;
    }

    public void setPricePerPound(BigDecimal pricePerPound) {
        this.pricePerPound = pricePerPound;
    }

    public BigDecimal getQuantityPrice() {
        return quantityPrice;
    }

    public void setQuantityPrice(BigDecimal quantityPrice) {
        this.quantityPrice = quantityPrice;
    }
}
