package com.example.andreea.gnbapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AsyncResponse {
    private Spinner spinner;
    private TableLayout tableTransactions;
    private TextView totalTextView;
    private List<Rate> rates;
    private List<Rate> directRatesToEuro;
    private HashMap<String, List<String>> hashMap;
    private double sumTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.products_spinner);
        tableTransactions = (TableLayout) findViewById(R.id.tableTransactions);
        totalTextView = (TextView) findViewById(R.id.totalSum);

        //set dynamic adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        //select listener on spinner
        spinner.setOnItemSelectedListener(this);

        rates = new ArrayList<Rate>();
        directRatesToEuro = new ArrayList<Rate>();
        hashMap = new HashMap<String, List<String>>();
        //get all rates
        GetRatesTask asyncTask = new GetRatesTask();
        asyncTask.delegate = this;
        asyncTask.execute();

        //populate list with products
        GetProductsTask getProductsTask = new GetProductsTask();
        Object object[] = new Object[2];
        object[0] = spinnerAdapter;
        object[1] = spinner;
        getProductsTask.execute(object);

        //init table header
        initTableHeader();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item was selected
        String itemProduct = parent.getItemAtPosition(position).toString();
        // clean table
        int count = tableTransactions.getChildCount();
        if (count > 1) {
            tableTransactions.removeViews(1, count - 1);
        }
        // get transactions of item
        DataParser dataParser = new DataParser();
        List<Transaction> transactionList = dataParser.getTransactionsForProduct(itemProduct);
        sumTotal = 0;
        drawTableRows(transactionList);
        totalTextView.setText("Total sum is : " + Math.round(sumTotal * 100.0)/100.0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initTableHeader() {

        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.GRAY);
        tbrow0.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        TextView tv0 = new TextView(this);
        tv0.setText("Product");
        tv0.setPadding(15,5,15,5);
        tv0.setTextColor(Color.WHITE);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("Amount");
        tv1.setTextColor(Color.WHITE);
        tv1.setPadding(15,5,15,5);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Currency");
        tv2.setTextColor(Color.WHITE);
        tv2.setPadding(15,5,15,5);
        tbrow0.addView(tv2);
        tableTransactions.addView(tbrow0, new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void drawTableRows(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            TableRow tbrow = new TableRow(this);
            tbrow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TextView t1v = new TextView(this);
            String name = transaction.getProduct();
            t1v.setText(name);
            t1v.setPadding(15,5,15,5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            Double amount = transaction.getAmount();
            t2v.setText(String.valueOf(amount));
            t2v.setPadding(15,5,15,5);
            tbrow.addView(t2v);

            TextView t3v = new TextView(this);
            String currency = transaction.getCurrency();
            if (currency.compareTo(Constants.EURO) == 0) {
                sumTotal += amount;
            } else { // transform to EURO
                double euroAmount = getEuroAmount(currency, amount);
                sumTotal += euroAmount;
            }
            t3v.setText(currency);
            t3v.setPadding(15,5,15,5);
            tbrow.addView(t3v);
            if (i % 2 == 0) {
                tbrow.setBackgroundColor(Color.DKGRAY);
                t1v.setTextColor(Color.WHITE);
                t2v.setTextColor(Color.WHITE);
                t3v.setTextColor(Color.WHITE);
            } else {
                tbrow.setBackgroundColor(Color.LTGRAY);
                t1v.setTextColor(Color.BLACK);
                t2v.setTextColor(Color.BLACK);
                t3v.setTextColor(Color.BLACK);
            }
            tableTransactions.addView(tbrow);
        }
    }

    public double getEuroAmount(String currency, double amount) {
        for (int i = 0; i < directRatesToEuro.size(); i++) {
            Rate rate = directRatesToEuro.get(i);
            // know a transform it the same way on reverse
            if (currency.compareTo(rate.getFrom()) == 0) {
                return rate.getRate() * amount;
            }
        }

        for (int i = 0; i < rates.size(); i++) {

        }

        return 0;
    }

    @Override
    public void processFinish(List<Rate> output) {
        this.rates.addAll(output);
        for (int i = 0; i < output.size(); i++) {
            Rate rate = output.get(i);
            if (rate.getTo().compareTo(Constants.EURO) == 0) {
                this.directRatesToEuro.add(rate);
            }
            if (hashMap.containsKey(output.get(i).getFrom())) {
                List<String> values = hashMap.get(output.get(i).getFrom());
                values.add(output.get(i).getTo());
            } else {
                List<String> newValues = new ArrayList<String>();
                newValues.add(output.get(i).getTo());
                hashMap.put(output.get(i).getFrom(), newValues);
            }
        }
    }
}
