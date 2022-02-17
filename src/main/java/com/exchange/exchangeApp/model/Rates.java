package com.exchange.exchangeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    private double CHF;
    private double USD;
    private double TRY;

    public Rates(double CHF, double USD, double TRY) {
        this.CHF = CHF;
        this.USD = USD;
        this.TRY = TRY;
    }

    public double getCHF() {
        return CHF;
    }

    public void setCHF(double CHF) {
        this.CHF = CHF;
    }

    public double getUSD() {
        return USD;
    }

    public void setUSD(double USD) {
        this.USD = USD;
    }

    public double getTRY() {
        return TRY;
    }

    public void setTRY(double TRY) {
        this.TRY = TRY;
    }
}
