package com.example.andreea.gnbapplication;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Andreea on 9/27/2016.
 */
public class GetRatesTask extends AsyncTask<Void, Void, List<Rate>> {
    public AsyncResponse delegate = null;
    @Override
    protected List<Rate> doInBackground(Void... params) {
        URL url;
        String response = "";
        List<Rate> rates = null;
        try {
            url = new URL(Constants.RATES);
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
            DataParser dataParser = new DataParser();
            rates = dataParser.getRates(response);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rates;
    }

    @Override
    protected void onPostExecute(List<Rate> rates) {
        delegate.processFinish(rates);
    }
}
