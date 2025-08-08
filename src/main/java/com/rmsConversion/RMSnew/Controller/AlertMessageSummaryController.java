package com.rmsConversion.RMSnew.Controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class AlertMessageSummaryController {

	@Autowired
	private AlertMessageSummaryService alertService;

	@Autowired
	private UserService userService;

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

}