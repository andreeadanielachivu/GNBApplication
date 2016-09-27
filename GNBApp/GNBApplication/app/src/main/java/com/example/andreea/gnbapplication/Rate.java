package com.example.andreea.gnbapplication;

/**
 * Created by Andreea on 9/27/2016.
 */
public class Rate {
    private String from = "";
    private String to = "";
    private Double rate;

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public Double getRate() {
        return this.rate;
    }
}
