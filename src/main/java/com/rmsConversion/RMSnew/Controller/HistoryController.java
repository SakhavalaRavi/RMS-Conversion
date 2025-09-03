package com.rmsConversion.RMSnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

import java.util.List;

import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.HistoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HistoryController {
    
    @Autowired
    private HistoryService historyService;
    
    @RequestMapping(value = "/getdHistoryLocation/{deviceid}/{startdate}/{enddate}/{max}", produces = {
            "application/json" }, method = RequestMethod.GET)
    public List<Object[]> getdHistoryLocation(@PathVariable("deviceid") Long deviceid,
            @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate,
            @PathVariable("max") Long max) {
        return historyService.getdeviceHistoryLocation(deviceid, startdate, enddate, max);
    }

    @RequestMapping(value = "/getaHistoryLocation/{deviceid}/{startdate}/{enddate}/{max}", produces = {
            "application/json" }, method = RequestMethod.GET)
    public List<Object[]> getdaHistoryLocation(@PathVariable("deviceid") Long deviceid,
            @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate,
            @PathVariable("max") Long max) {
        return historyService.getadminHistoryLocation(deviceid, startdate, enddate, max);
    }

    @RequestMapping(value = "/playbackdata", produces = {"application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getPlaybackData(
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam int did,
            @RequestParam String datatype) {
        return historyService.getPlaybackData(did, sdate, edate, datatype);
    }

    @RequestMapping(value = "/alldDetailsFromHistory", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getHistoryData(
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam int did) {
        return historyService.getHistoryData(did, sdate, edate);
    }

    @RequestMapping(value = "/liveDataFromLasttrack/{deviceId}", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<?> getByDeviceIdAndDates(@PathVariable Long deviceId) {
        try {
            List<Map<String, Object>> data = historyService.getAllByDeviceIdAndDateRange(deviceId);
            Map<String, Object> response = new HashMap<>();
            response.put("data", data);
            response.put("count", data.size());
            response.put("status", data.isEmpty() ? "fail" : "success");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
