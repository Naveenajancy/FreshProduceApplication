package com.freshproduceapp.model;

import java.math.BigDecimal;

public class FreshProduce {
    private Integer freshProduceId;
    private String freshProduceName;
    private BigDecimal pricePerPound;
    private Double availablePounds;

    public Integer getFreshProduceId() {
        return freshProduceId;
    }

    public void setFreshProduceId(Integer freshProduceId) {
        this.freshProduceId = freshProduceId;
    }

    public String getFreshProduceName() {
        return freshProduceName;
    }

    public void setFreshProduceName(String freshProduceName) {
        this.freshProduceName = freshProduceName;
    }

    public BigDecimal getPricePerPound() {
        return pricePerPound;
    }

    public void setPricePerPound(BigDecimal pricePerPound) {
        this.pricePerPound = pricePerPound;
    }

    public Double getAvailablePounds() {
        return availablePounds;
    }

    public void setAvailablePounds(Double availablePounds) {
        this.availablePounds = availablePounds;
    }

    @Override
    public String toString() {
        return "FreshProduce{" +
                "freshProduceId=" + freshProduceId +
                ", freshProduceName='" + freshProduceName + '\'' +
                ", pricePerPound=" + pricePerPound +
                ", availablePounds=" + availablePounds +
                '}';
    }
}
