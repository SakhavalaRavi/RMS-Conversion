package com.rmsConversion.RMSnew.common.utils;

import com.rmsConversion.RMSnew.DTO.GeocodeResponse;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class Geocoder {
    private Gson gson = new Gson();
    private volatile long lastRequest = 0L;

    public GeocodeResponse getLocation(String... addressElements) throws JsonSyntaxException, JsonIOException, MalformedURLException,
            IOException {
        StringBuilder sb = new StringBuilder();
        for (String string : addressElements) {
            if (sb.length() > 0) {
                sb.append('+');
            }
            sb.append(URLEncoder.encode(string.replace(' ', '+'), "UTF-8"));
        }
        String url = "http://maps.googleapis.com/maps/api/geocode/json?sensor=false&address=" + sb.toString();
        synchronized (this) {
            try {
                long elapsed = System.currentTimeMillis() - lastRequest;
                if (elapsed < 100) {
                    try {
                        Thread.sleep(100 - elapsed);
                    } catch (InterruptedException e) {
                    }
                }
                return gson.fromJson(new BufferedReader(new InputStreamReader(new URL(url).openStream())), GeocodeResponse.class);
            } finally {
                lastRequest = System.currentTimeMillis();
            }
        }
    }

    public static void main(String[] args) throws JsonSyntaxException, JsonIOException, MalformedURLException, IOException {

        GeocodeResponse gr = new Geocoder().getLocation("18.34634,77.235235");
        if (!gr.getStatus().toString().equals("OVER_QUERY_LIMIT")) {
            List location = gr.getResults().get(0).getAddress_components();

            GeocodeResponse.Result.AddressComponent adr = (GeocodeResponse.Result.AddressComponent) location.get(0);
            System.out.println(adr.getShort_name() + "::" + adr.getLong_name() + "::" + adr.getTypes());
        }
    }
}
