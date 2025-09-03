package com.rmsConversion.RMSnew.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rmsConversion.RMSnew.Service.HistoryServices;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@Transactional
@RestController
@RequestMapping("/api")
public class HistoryController {
    
    @Autowired
    private HistoryServices historyServices;
    
	private static final Logger log = LoggerFactory.getLogger(HistoryController.class);


    @RequestMapping(value = "/getdHistoryLocation/{deviceid}/{startdate}/{enddate}/{max}", produces = {
        "application/json" }, method = RequestMethod.GET)
public List<Object[]> getdHistoryLocation(@PathVariable("deviceid") Long deviceid,
        @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate,@PathVariable("max") Long max) {
			log.info("getdHistoryLocation method called with deviceid: {}, startdate: {}, enddate: {}, max: {}", deviceid, startdate, enddate, max);
    return historyServices.getdeviceHistoryLocation(deviceid, startdate, enddate,max);

}

@RequestMapping(value = "/getaHistoryLocation/{deviceid}/{startdate}/{enddate}/{max}", produces = {"application/json" }, method = RequestMethod.GET)
	public List<Object[]> getdaHistoryLocation(@PathVariable("deviceid") Long deviceid,@PathVariable("startdate") String startdate,@PathVariable("enddate") String enddate,@PathVariable("max") Long max){
		return historyServices.getadminHistoryLocation(deviceid, startdate, enddate,max);
		
	}
	
	@RequestMapping(value = "/playbackdata", produces = {"application/json" }, method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getPlaybackData(
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam int did,
            @RequestParam String datatype) {

        return historyServices.getPlaybackData(did, sdate, edate, datatype);
    }
	@RequestMapping(value = "/alldDetailsFromHistory", produces = {"application/json"}, method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getHistoryData(
	        @RequestParam String sdate,
	        @RequestParam String edate,
	        @RequestParam int did) {

	    return historyServices.getHistoryData(did, sdate, edate);
	}
	
	@RequestMapping(value = "/liveDataFromLasttrack/{deviceId}", produces = {"application/json"}, method = RequestMethod.GET)
	public ResponseEntity<?> getByDeviceIdAndDates(
	    @PathVariable Long deviceId ){
	    
	    try {
	        
	        List<Map<String, Object>> data = historyServices.getAllByDeviceIdAndDateRange(deviceId);
	        Map<String, Object> response = new HashMap<>();
	        response.put("data", data);
	        response.put("count", data.size());
	        response.put("status", data.isEmpty() ? "fail" : "success");
	        
	        return ResponseEntity.ok(response);
	    }
	    catch (Exception e) {
	        Map<String, Object> errorResponse = new HashMap<>();
	        errorResponse.put("status", "fail");
	        errorResponse.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(errorResponse);
	    }
	}
}
