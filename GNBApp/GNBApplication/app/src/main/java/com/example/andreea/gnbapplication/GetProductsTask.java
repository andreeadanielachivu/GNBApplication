package com.example.andreea.gnbapplication;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Andreea on 9/26/2016.
 */
public class GetProductsTask extends AsyncTask<Object, Void, String> {
    ArrayAdapter<String> adapter = null;
    Spinner spinner = null;

    @Override
    protected String doInBackground(Object... params) {
        adapter = (ArrayAdapter<String>) params[0];
        spinner = (Spinner)params[1];
        URL url;
        String response = "";
        try {
            url = new URL(Constants.TRANSACTIONS);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            conn.connect();

            int responseCode=conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        ListProductsTask task = new ListProductsTask();
        Object []objects = new Object[3];
        objects[0] = s;
        objects[1] = adapter;
        objects[2] = spinner;
        task.execute(objects);
    }
}
