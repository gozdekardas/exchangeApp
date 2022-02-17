package com.exchange.exchangeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {
    private Double Rate;

    public Rate() {
    }

    public Double getRate() {
        return Rate;
    }

    public void setRate(Double rate) {
        Rate = rate;
    }
}
