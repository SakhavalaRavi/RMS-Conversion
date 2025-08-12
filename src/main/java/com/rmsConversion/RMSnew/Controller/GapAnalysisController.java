package com.rmsConversion.RMSnew.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Service.GapAnalysisService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class GapAnalysisController {

	@Autowired
	private GapAnalysisService gapService;

	@GetMapping("/gapAnalysisReport")
	public ResponseEntity<List<Map<String, Object>>> getGapAnalysisReport(
	        @RequestParam Long deviceId,
	        @RequestParam String startDate, 
	        @RequestParam String endDate, 
	        @RequestParam long intervalInSeconds) {
	    
	    try {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date start = formatter.parse(startDate);
	        Date end = formatter.parse(endDate);
	        
	        List<Map<String, Object>> result = gapService.getGapReport(deviceId, start, end, intervalInSeconds);
	        
	        return ResponseEntity.ok(result);
	        
	    } catch (ParseException e) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("error", "Invalid date format.");
	        return ResponseEntity.badRequest().body(Arrays.asList(error));
	        
	    } catch (Exception e) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("error", "Internal server error");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Arrays.asList(error));
	    }
	}
}
