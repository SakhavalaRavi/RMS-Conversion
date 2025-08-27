package com.rmsConversion.RMSnew.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.PoiData;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Service.PoiService;
import com.rmsConversion.RMSnew.Service.UserService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api")
public class POIController {

	@Autowired
	private PoiService poiService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/addPOIData/{managerId}")
	public PoiData addPoi(@RequestBody PoiData poiData, @PathVariable Long managerId) {
		poiData.setManagerId(managerId);
		return poiService.addPoi(poiData);
	}

	@GetMapping("/getAllPOIData/{managerId}")
	public List<PoiData> getAllPois(@PathVariable Long managerId) {
		return poiService.getAllPoisByManagerId(managerId);
	}

	@PutMapping("/updatePOIData/{id}/{managerId}")
	public ResponseEntity<PoiData> updatePoi(
			@PathVariable Long id,
			@PathVariable Long managerId,
			@RequestBody PoiData updatedPoi) {

		PoiData result = poiService.updatePoi(id, managerId, updatedPoi);
		if (result == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result);
	}

	@DeleteMapping("/deletePOIData/{id}/{managerId}")
	public String deletePoi(@PathVariable Long id, @PathVariable Long managerId) {
		boolean deleted = poiService.deletePoi(id, managerId);
		if (deleted) {
			return "POI with ID " + id + " has been deleted.";
		} else {
			return "POI with ID " + id + " not found or unauthorized.";
		}
	}
	
	@GetMapping("/getPoiReport")
    public ResponseEntity<?> getPoiReport(
            @RequestParam Long deviceId,
            @RequestParam Long managerId,
            @RequestParam String sdate,
            @RequestParam String edate,
            @RequestParam String poiname,
            @RequestParam int radius) {
        try {
            Object response = poiService.getPoiHistoryReport(deviceId, managerId, sdate, edate, poiname, radius);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to generate report"));
        }
    }
	
	@GetMapping("/getPoiReportByUser")
	public ResponseEntity<?> getPoiReportByUser(@RequestParam Long deviceId, @RequestParam Long userId,
			@RequestParam String sdate, @RequestParam String edate, @RequestParam String poiname,
			@RequestParam int radius) {
		try {
			User user = userService.getuserbyid(userId);
			Long managerId = user.getAddedby();
			Object response = poiService.getPoiHistoryReport(deviceId, managerId, sdate, edate, poiname, radius);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Failed to generate report"));
		}
	}
	
	@GetMapping("/getTripReport")
	public ResponseEntity<?> getTripReport(@RequestParam String did,
	                                       @RequestParam int mintrip,
	                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String fromdate,
	                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String todate) {
	    try {
	        String[] deviceIdStrings = did.split(",");
	        List<Long> deviceIds = Arrays.stream(deviceIdStrings)
	                                     .map(Long::parseLong)
	                                     .collect(Collectors.toList());

	        Object tripreport = poiService.getTripReport(deviceIds, mintrip, fromdate, todate);
	        return ResponseEntity.status(HttpStatus.OK).body(tripreport);
	    } catch (Exception e) {
	        Map<String, Object> error = new HashMap<>();
	        error.put("status", false);
	        error.put("message", "Failed to generate trip report");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	}
}
