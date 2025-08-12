package com.rmsConversion.RMSnew.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.Parameter;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Repository.DeviceProfileRepository;
import com.rmsConversion.RMSnew.Repository.HistoryRepository;
import com.rmsConversion.RMSnew.Service.GenericApiService;
import com.rmsConversion.RMSnew.Service.HistoryService;

import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class GenericApiController {

	@Autowired
	GenericApiService genericApiService;

	@Autowired
	private HistoryService historyService;

	@Autowired
	private HistoryRepository historyRepository;

	

	@Autowired
	private DeviceProfileRepository DpService;

	private static final Logger log = LoggerFactory.getLogger(GenericApiController.class);
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat reporteHourDf = new SimpleDateFormat("HH:mm");
	SimpleDateFormat reporteDateDf = new SimpleDateFormat("dd-MMM");
	SimpleDateFormat reporteYearDf = new SimpleDateFormat("yyyy");

	@PostMapping("/GetGenericParameters")
	public List<Parameter> GetGenericParameters(@RequestBody List<Long> ids) {
		return genericApiService.getParametersByIds(ids);

	}

    @PostMapping(value = "/api/GetDeviceChartRecords", produces = "application/json")
	public String GetDeviceChartRecords(@RequestBody Map<String, Object> request)
			throws JsonGenerationException, JsonMappingException, IOException, ParseException {

		Long paramLong = Long.parseLong(request.get("id").toString());
		Devicemaster device = dmrepo.findBydeviceid(paramLong);
		long profileId = device.getDp().getPrid();
		String prmnameStr = request.get("prmname").toString();
		List<String> prmnameList = Arrays.stream(prmnameStr.split(",")).map(String::trim).collect(Collectors.toList());
		int limit = request.containsKey("limit") ? Integer.parseInt(request.get("limit").toString()) : 0;

		JSONObject finalResponse = new JSONObject();
		for (String prmname : prmnameList) {
			Map<String, Object> paramInfo = genericApiService.getParameterIdByNameForGenericDashboard(profileId,
					prmname);
			if (paramInfo == null || paramInfo.isEmpty()) {
				log.warn("Parameter not found for name: " + prmname);
				continue;
			}

			String prmId = paramInfo.get("parameterId").toString();
			String prmType = paramInfo.get("parameterType").toString();

			List<Object[]> list = dashboardService.GetDeviceParameterRecords(paramLong, prmId, limit);
			log.info("Chart data for " + prmname + " (" + prmId + "): " + list.size() + " records");

			JSONArray chartData = new JSONArray();
			try {
				for (Object[] record : list) {
					String deviceDate = record[0].toString();
					String data = record[1].toString();
					Date date = df.parse(deviceDate);
					long millis = date.getTime();

					JSONObject point = new JSONObject();
					point.put("x", millis);
					point.put("y", data);
					chartData.put(point);
				}
			} catch (Exception e) {
				log.warn("Error parsing data: " + e.getMessage());
			}

			JSONObject chartContent = new JSONObject();

			chartContent.put("prmtype", prmType);
			chartContent.put("series",
					new JSONArray().put(new JSONObject().put("name", prmname).put("data", chartData)));
			chartContent.put("type", "line");

			finalResponse.put(prmname, chartContent);
		}

		return finalResponse.toString();
	}

    @GetMapping("/api/getProfileIdByProfileName")
	public ResponseEntity<?> getProfileIdByProfileName(@RequestParam String profileName) {
		Long profileId = genericApiService.getProfileIdByProfileName(profileName);
		if (profileId != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("profileName", profileName);
			response.put("profileId", profileId);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/api/getParameterIdByProfileId")
	public ResponseEntity<?> getParameterIdByProfileId(@RequestParam Long prid, @RequestParam List<String> names) {

		List<Map<String, Object>> result = genericApiService.getParameterIdsByNames(prid, names);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/api/getParameterBydeviceId/{deviceId}")
	public ResponseEntity<List<Map<String, Object>>> getParameterBydeviceId(@PathVariable Long deviceId) {
		List<Map<String, Object>> parameters = genericApiService.getParametersByDeviceId(deviceId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/api/getRecordsForMeter/{deviceId}")
	public ResponseEntity<List<Map<String, Object>>> getRecordsForMeter(@PathVariable Long deviceId) {
		List<Map<String, Object>> parameters = genericApiService.getParametersByDeviceIdMinAndMax(deviceId);
		return ResponseEntity.ok(parameters);
	}

}
