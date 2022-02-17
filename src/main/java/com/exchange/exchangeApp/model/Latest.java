package com.exchange.exchangeApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Latest {
    private Boolean success;
    private int  timestamp;
    private String base;
    private Date date;
    private Rates rates;

    public Latest(Boolean success, int timestamp, String base, Date date, Rates rates) {
        this.success = success;
        this.timestamp = timestamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}
