package com.rmsConversion.RMSnew.common.utils;

import com.rmsConversion.RMSnew.Model.History;

import java.util.*;

public class TripHelper {

    public static boolean getIgnitionStatus(History history, String digitalKey) {
        try {
            Map<String, Object> deviceData = history.getDevicedata();
            if (deviceData == null || !deviceData.containsKey("Digital")) return false;

            Object digitalVal = ((Map<?, ?>) deviceData.get("Digital")).get(digitalKey);
            if (digitalVal == null) return false;

            String val = digitalVal.toString();
            return val.equals("1") || val.equalsIgnoreCase("true");
        } catch (Exception e) {
            return false;
        }
    }

    public static double calculateTripDistance(List<History> list, Date start, Date end) {
        Distance distanceUtil = new Distance();
        double totalDistance = 0.0;

        for (int i = 0; i < list.size() - 1; i++) {
            History h1 = list.get(i);
            History h2 = list.get(i + 1);

            if (h1.getDeviceDate().before(start) || h2.getDeviceDate().after(end)) continue;

            try {
                Map<String, Object> gps1 = h1.getGpsdata();
                Map<String, Object> gps2 = h2.getGpsdata();

                double lat1 = Double.parseDouble(gps1.get("latitude").toString());
                double lon1 = Double.parseDouble(gps1.get("longitude").toString());
                double lat2 = Double.parseDouble(gps2.get("latitude").toString());
                double lon2 = Double.parseDouble(gps2.get("longitude").toString());

                double dist = distanceUtil.getDistanceFromPosition(lat1, lon1, lat2, lon2);
                if (dist < 0.02) continue;

                totalDistance += dist;
            } catch (Exception ignored) {
            }
        }

        return totalDistance;
    }

    public static String getLocation(Map<String, Object> gps) {
        try {
            String lat = gps.get("latitude").toString();
            String lng = gps.get("longitude").toString();
            GGcoder geocoder = new GGcoder();
            return geocoder.tinygeocoder(lat, lng);
        } catch (Exception e) {
            return "N/A";
        }
    }

    public static String formatDuration(Date start, Date end) {
        long durationMillis = end.getTime() - start.getTime();
        long hours = durationMillis / (1000 * 60 * 60);
        long minutes = (durationMillis / (1000 * 60)) % 60;
        long seconds = (durationMillis / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static Map<String, Object> buildResponse(boolean status, String message, List<?> data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
}
