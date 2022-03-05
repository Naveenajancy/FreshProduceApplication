package com.freshproduceapp.model;

public class PurchaseOrder {
    private Integer id;
    private Double requiredPounds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRequiredPounds() {
        return requiredPounds;
    }

    public void setRequiredPounds(Double requiredPounds) {
        this.requiredPounds = requiredPounds;
    }
}
