package com.freshproduceapp.model;

import java.math.BigDecimal;

public class CustomerPurchaseItem {
    private String name;
    private Double purchasedPounds;
    private BigDecimal costPerPound;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPurchasedPounds() {
        return purchasedPounds;
    }

    public void setPurchasedPounds(Double purchasedPounds) {
        this.purchasedPounds = purchasedPounds;
    }

    public BigDecimal getCostPerPound() {
        return costPerPound;
    }

    public void setCostPerPound(BigDecimal costPerPound) {
        this.costPerPound = costPerPound;
    }
}
