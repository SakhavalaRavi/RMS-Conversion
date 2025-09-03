package com.rmsConversion.RMSnew.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.DTO.ActiveAlertDTO;
import com.rmsConversion.RMSnew.DTO.AlertCountBySiteDTO;
import com.rmsConversion.RMSnew.DTO.AlertDurationDTO;
import com.rmsConversion.RMSnew.DTO.AlertFrequency;
import com.rmsConversion.RMSnew.DTO.AlertStatusDTO;
import com.rmsConversion.RMSnew.DTO.AlertTrendDto;
import com.rmsConversion.RMSnew.DTO.SiteStatsDTO;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.AlertMessageSummaryService;
import com.rmsConversion.RMSnew.Service.UserService;
import com.rmsConversion.RMSnew.springSecurity.JwtUtil;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class AlertMessageSummaryController {

	private static final Logger logger = LoggerFactory.getLogger(AlertMessageSummaryController.class);

	@Autowired
	private AlertMessageSummaryService alertService;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/alertCountByType/{fromDate}/{toDate}/{uid}")
	public List<AlertStatusDTO> countByType(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {

		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getAlertCountsByTypeForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getAlertCountsByType(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getActiveAlert/{fromDate}/{toDate}/{uid}")
	public List<ActiveAlertDTO> active(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getActiveAlertsForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getActiveAlerts(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getAlertBySite/{fromDate}/{toDate}/{uid}")
	public List<AlertCountBySiteDTO> bySite(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getAlertsBySiteAndType1ForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getAlertsBySiteAndType1(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getAvarageDuration/{fromDate}/{toDate}/{uid}")
	public List<AlertDurationDTO> getAvgDurationByAlertType(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getAvgDurationByAlertTypeForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getAvgDurationByAlertType(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getTopFiveSite/{uid}")
	public List<SiteStatsDTO> topFiveSitesLast24h(@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.top5SitesLast24hForUser(uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.top5SitesLast24h(uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getTopFiveAlert/{uid}")
	public List<AlertStatusDTO> top5AlertsLast24h(@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.top5AlertsLast24hForUser(uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.top5AlertsLast24h(uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getActiveAlertByType/{fromDate}/{toDate}/{uid}")
	public List<AlertStatusDTO> activeByType(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getActiveAlertsByTypeForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getActiveAlertsByType(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/alert-trend/{fromDate}/{toDate}/{uid}")
	public List<AlertTrendDto> getTrend(
			@PathVariable("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fromDate,
			@PathVariable("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date toDate,
			@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getTrendForUser(fromDate, toDate, uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getTrend(fromDate, toDate, uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/getResolutionTimeTrend/{uid}")
	public List<Map<String, Object>> getResolutionTimeTrend(@PathVariable("uid") Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getLast30DaysTrendForUSer(uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getLast30DaysTrend(uid);
			}
		}

		return Collections.emptyList();
	}

	@GetMapping("/frequencyHitmapOfLastWeek/{uid}")
	public List<AlertFrequency> frequencyLast7Days(@PathVariable Long uid) {
		User user = userService.getuserbyid(uid);

		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				return alertService.getLast7DaysFrequencyForUser(uid);
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				return alertService.getLast7DaysFrequency(uid);
			}
		}

		return Collections.emptyList();
	}


	@GetMapping(value = "/getRecentAlerts/{fromdate}/{todate}/{size}/{page}/{status}/{uid}", 
            produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<String> getRecentAlerts(
        @RequestHeader("Authorization") String authorizationHeader,
        @PathVariable String fromdate,
        @PathVariable String todate,
        @PathVariable int size,
        @PathVariable int page,
        @PathVariable String status,
        @PathVariable Long uid,
        @RequestParam(value = "deviceId", required = false) Long deviceId,
        @RequestParam(value = "parameterId", required = false) Long parameterId)
        throws JSONException, ParseException {

    logger.info("getRecentAlerts called with params: fromdate={}, todate={}, size={}, page={}, status={}, uid={}, deviceId={}, parameterId={}", 
                fromdate, todate, size, page, status, uid, deviceId, parameterId);

    Map<String, Object> response = new HashMap<>();

    // 🔑 Extract token
    String token = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
    }

    if (token == null || !jwtUtil.validateToken(token)) {
        logger.error("Invalid or missing JWT token");
        response.put("status", "error");
        response.put("message", "Invalid or missing JWT token");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JSONObject(response).toString());
    }

    // ✅ Continue with original logic
    if (page < 1)
        page = 1;

    User user = userService.getuserbyid(uid);
    if (user == null) {
        logger.error("User not found with id: {}", uid);
        response.put("status", "error");
        response.put("message", "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new JSONObject(response).toString());
    }

    boolean isManager = user.getUserRole().stream()
            .anyMatch(r -> "ROLE_MANAGER".equalsIgnoreCase(r.getRole().toString()));

    logger.info("User {} isManager: {}", uid, isManager);

    int totalCount;
    List<Object[]> rows;

    if (isManager) {
        totalCount = alertService.getRecentAlertsCount(fromdate, todate, deviceId, status, parameterId, uid);
        rows = alertService.getRecentAlertsPage(fromdate, todate, deviceId, status, size, page, parameterId, uid);
        logger.info("Manager query - totalCount: {}, rows returned: {}", totalCount, rows != null ? rows.size() : 0);
    } else {
        totalCount = alertService.getRecentAlertsCountForUser(fromdate, todate, deviceId, status, parameterId, uid);
        rows = alertService.getRecentAlertsPageForUser(fromdate, todate, deviceId, status, size, page, parameterId, uid);
        logger.info("User query - totalCount: {}, rows returned: {}", totalCount, rows != null ? rows.size() : 0);
    }

    if (rows == null || rows.isEmpty()) {
        logger.warn("No data returned from database for user: {}", uid);
    }

    JSONObject resp = new JSONObject();
    JSONArray cols = new JSONArray().put(new JSONArray().put("#")).put(new JSONArray().put("Site Name"))
            .put(new JSONArray().put("Device ID")).put(new JSONArray().put("Alert Name"))
            .put(new JSONArray().put("Start Time")).put(new JSONArray().put("End Time"))
            .put(new JSONArray().put("Duration")).put(new JSONArray().put("Since"))
            .put(new JSONArray().put("Status"));
    resp.put("columns", cols);

    JSONArray data = new JSONArray();
    int sr = (page - 1) * size + 1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    for (Object[] r : rows) {
        JSONArray row = new JSONArray();
        row.put(sr++);
        row.put(r[0]);
        row.put(r[1]);
        row.put(r[2]);
        row.put(r[3]);
        Object endTime = r[4];
        row.put(endTime != null ? endTime.toString() : JSONObject.NULL);
        row.put(r[5]);
        String sinceText = "*Ongoing*";
        if (endTime != null) {
            try {
                Date start = sdf.parse(r[3].toString());
                Date end = sdf.parse(endTime.toString());
                long diffSeconds = (end.getTime() - start.getTime()) / 1000;
                long minutes = diffSeconds / 60;
                long seconds = diffSeconds % 60;
                sinceText = minutes + " min " + seconds + " sec";
            } catch (Exception e) {
                sinceText = "N/A";
            }
        }
        row.put(sinceText);
        row.put(r[6]);
        data.put(row);
    }

    JSONObject pg = new JSONObject();
    pg.put("totalItems", totalCount);
    pg.put("totalPages", (int) Math.ceil((double) totalCount / size));
    pg.put("currentPage", page);
    pg.put("pageSize", size);
    resp.put("pagination", pg);
    resp.put("data", data);

    return ResponseEntity.ok(resp.toString());
}

@GetMapping("/getDevicesWithParameter/{uid}")
	public List<Map<String, Object>> getDeviceResponse(@PathVariable Long uid,
			@RequestParam(value = "deviceId", required = false) Long deviceId) {
		User user = userService.getuserbyid(uid);

		List<Object[]> profileList = null;
		for (UserRole role : user.getUserRole()) {
			String r = role.getRole().toString();
			if ("ROLE_USER".equalsIgnoreCase(r)) {
				profileList = alertService.Assigndeviceprofilebyuid(uid);
				break;
			} else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
				profileList = alertService.assigndeviceprofilebymanagerid(uid);
				break;
			}
		}
		if (profileList == null) {
			profileList = Collections.emptyList();
		}

		return alertService.getDeviceResponseGrouped(deviceId, profileList);
	}

}