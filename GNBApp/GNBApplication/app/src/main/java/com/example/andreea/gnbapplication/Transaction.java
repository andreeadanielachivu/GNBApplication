package com.example.andreea.gnbapplication;

/**
 * Created by Andreea on 9/27/2016.
 */
public class Transaction {
    private String product = "";
    private double amount;
    private String currency = "";

    public Transaction() {

    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setAmount (double amount) {
        this.amount = amount;
    }

    public void setCurrency (String currency) {
        this.currency = currency;
    }

    public String getProduct() {
        return this.product;
    }

    public String getCurrency() {
        return this.currency;
    }

    public double getAmount() {
        return this.amount;
    }
}

