package com.rmsConversion.RMSnew.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

import com.rmsConversion.RMSnew.DTO.GeocodeResponse;

public class GGcoder {

	public static int getRandom(int from, int to) {
		if (from < to) {
			return from + new Random().nextInt(Math.abs(to - from));
		}
		return from - new Random().nextInt(Math.abs(to - from));
	}

	public String tinygeocoder(String lat, String lang) throws MalformedURLException, IOException {
		String locationdata = "";
		try {
	
			URL oracle = new URL("http://" + "loc.apnatracker.in/bspGeov4/?geo=" + lat + "," + lang);

			URLConnection yc = oracle.openConnection();
			yc.setReadTimeout(50000);
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				locationdata = locationdata + inputLine;
			}
			in.close();
		} catch (Exception ex) {
			try {
				GeocodeResponse gr = new Geocoder().getLocation(new String[] { lat + "," + lang });
				locationdata = ((GeocodeResponse.Result) gr.getResults().get(0)).getFormatted_address() + "(*)";
			} catch (Exception ex2) {
				locationdata = "N/A";
			}
		}
		if (locationdata.length() < 4) {
			try {
				GeocodeResponse gr = new Geocoder().getLocation(new String[] { lat + "," + lang });
				locationdata = ((GeocodeResponse.Result) gr.getResults().get(0)).getFormatted_address() + "(*)";
			} catch (Exception ex4) {
				locationdata = "N/A";
			}
		}
		return locationdata;
	}

	public String tinygeocoder2(String latlang) throws MalformedURLException, IOException {
		String locationdata = "";
		try {
			URL oracle = new URL("http://" + "loc.apnatracker.in/bspGeov4/?geo=" + latlang);

			URLConnection yc = oracle.openConnection();
			yc.setReadTimeout(50000);
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				locationdata = locationdata + inputLine;
			}
			in.close();
		} catch (Exception ex) {

			GeocodeResponse gr = new Geocoder().getLocation(new String[] { latlang });
			locationdata = ((GeocodeResponse.Result) gr.getResults().get(0)).getFormatted_address() + "(*)";
		}
		if (locationdata.length() < 4) {
			locationdata = "N/A";
		}
		return locationdata;
	}

	private static String getmanualylocation(String latlang) throws MalformedURLException, IOException {
		String locationdata = "";
		try {
			URL oracle = new URL("http://rocket1.rocket1.cloudbees.net/?geo=" + latlang);

			URLConnection yc = oracle.openConnection();
			yc.setReadTimeout(50000);
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				locationdata = locationdata + inputLine;
			}
			in.close();
		} catch (Exception ex) {
			locationdata = "invalid location";
		}
		return locationdata;
	}

}
