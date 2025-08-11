package com.rmsConversion.RMSnew.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Service.GapAnalysisService;
import com.ibm.icu.text.SimpleDateFormat;

@RestController
@CrossOrigin(origins = { "*" })
public class GapAnalysisController {

    @Autowired
    private GapAnalysisService gapService;

    @GetMapping("/api/gapAnalysisReport")
    public ResponseEntity<List<Map<String, Object>>> getGapAnalysisReport(
            @RequestParam("deviceids") String deviceids,
            @RequestParam String startDate, 
            @RequestParam String endDate, 
            @RequestParam long intervalInSeconds) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = formatter.parse(startDate);
            Date end = formatter.parse(endDate);

            List<Long> deviceIdList = Arrays.stream(deviceids.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            List<Map<String, Object>> finalResult = new ArrayList<>();

            for (Long deviceId : deviceIdList) {
                List<Map<String, Object>> report = gapService.getGapReport(deviceId, start, end, intervalInSeconds);
                finalResult.addAll(report);
            }

            return ResponseEntity.ok(finalResult);

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
