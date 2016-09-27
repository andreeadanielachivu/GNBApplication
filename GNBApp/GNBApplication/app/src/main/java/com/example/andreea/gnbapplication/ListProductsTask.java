package com.example.andreea.gnbapplication;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreea on 9/27/2016.
 */
public class ListProductsTask extends AsyncTask<Object, Void, Set<String>> {
    ArrayAdapter<String> adapter = null;
    Set<String> products = null;
    Spinner spinner = null;
    @Override
    protected Set<String> doInBackground(Object... params) {
        String response = (String)params[0];
        adapter = (ArrayAdapter<String>)params[1];
        spinner = (Spinner)params[2];
        DataParser dataParser = new DataParser();
        products = dataParser.getProducts(response);
        return products;
    }

    @Override
    protected void onPostExecute(Set<String> set) {
        Iterator<String> iterator = set.iterator();
        while(iterator.hasNext()) {
            String setElement = iterator.next();
            adapter.add(setElement);
            adapter.notifyDataSetChanged();
        }
        spinner.setAdapter(adapter);
    }
}
