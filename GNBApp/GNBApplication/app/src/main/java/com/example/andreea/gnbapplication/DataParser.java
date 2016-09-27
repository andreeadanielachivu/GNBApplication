package com.example.andreea.gnbapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreea on 9/27/2016.
 */
public class DataParser {
    private static List<Transaction> transactions = null;

    public Set<String> getProducts(String data) {
        transactions = new ArrayList<Transaction>();
        Set<String> products = null;
        try {
            products = new HashSet<String>();
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                Transaction t = new Transaction();
                JSONObject jsonObject = json.getJSONObject(i);
                String name = jsonObject.getString("sku");
                products.add(name);
                t.setProduct(name);
                String currency = jsonObject.getString("currency");
                t.setCurrency(currency);
                Double amount = jsonObject.getDouble("amount");
                t.setAmount(amount);
                transactions.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    public List<Transaction> getTransactionsForProduct(String name) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            if (t.getProduct().compareTo(name) == 0) {
                transactionList.add(t);
            }
        }
        return transactionList;
    }

    public List<Rate> getRates(String data) {
        List<Rate> rates = new ArrayList<Rate>();
        try {
            JSONArray result = new JSONArray(data);
            for (int i = 0; i < result.length(); i++) {
                JSONObject jsonObject = result.getJSONObject(i);
                Rate rate = new Rate();
                String from = jsonObject.getString("from");
                rate.setFrom(from);
                String to = jsonObject.getString("to");
                rate.setTo(to);
                Double rateValue = jsonObject.getDouble("rate");
                rate.setRate(rateValue);
                rates.add(rate);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rates;
    }
}
