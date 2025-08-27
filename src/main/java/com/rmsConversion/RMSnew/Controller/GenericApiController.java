package com.rmsConversion.RMSnew.Controller;

import java.io.IOException;
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
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.rmsConversion.RMSnew.Model.Devicemaster;
import com.rmsConversion.RMSnew.Model.Parameter;
import com.rmsConversion.RMSnew.Repository.DevicemasterRepository;
import com.rmsConversion.RMSnew.Service.DashboardAPIService;
import com.rmsConversion.RMSnew.Service.DevicemasterServices;
import com.rmsConversion.RMSnew.Service.GenericApiService;
import com.rmsConversion.RMSnew.Service.HistoryServices;
import com.rmsConversion.RMSnew.Service.ParameterServices;
import com.rmsConversion.RMSnew.Service.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class GenericApiController {

	@Autowired
	GenericApiService genericApiService;

	@Autowired
	HistoryServices hstServide;

	@Autowired
	DevicemasterRepository dmrepo;

	@Autowired
	DashboardAPIService dashboardService;

	@Autowired
	DevicemasterServices deviceService;

	@Autowired
	UserService userService;

	@Autowired
	ParameterServices parameterService;

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

    @PostMapping(value = "/GetDeviceChartRecords", produces = "application/json")
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

    @GetMapping("/getProfileIdByProfileName")
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

	@GetMapping("/getParameterIdByProfileId")
	public ResponseEntity<?> getParameterIdByProfileId(@RequestParam Long prid, @RequestParam List<String> names) {

		List<Map<String, Object>> result = genericApiService.getParameterIdsByNames(prid, names);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/getParameterBydeviceId/{deviceId}")
	public ResponseEntity<List<Map<String, Object>>> getParameterBydeviceId(@PathVariable Long deviceId) {
		List<Map<String, Object>> parameters = genericApiService.getParametersByDeviceId(deviceId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/getRecordsForMeter/{deviceId}")
	public ResponseEntity<List<Map<String, Object>>> getRecordsForMeter(@PathVariable Long deviceId) {
		List<Map<String, Object>> parameters = genericApiService.getParametersByDeviceIdMinAndMax(deviceId);
		return ResponseEntity.ok(parameters);
	}

	@GetMapping("/getRecordsFormMultipalParameterForMeter")
	public ResponseEntity<?> getRecordsFormMultipalParameterForMeter(@RequestParam Long deviceId,
			@RequestParam String prmnames) {

		List<String> paramIdList = Arrays.stream(prmnames.split(",")).map(String::trim).collect(Collectors.toList());
		List<Map<String, Object>> results = new ArrayList<>();

		for (String paramId : paramIdList) {
			Map<String, Object> result = genericApiService.getParameterWithId(deviceId, paramId);
			if (result != null && !result.isEmpty()) {
				results.add(result);
			} else {
				Map<String, Object> error = new HashMap<>();
				error.put("parameterId", paramId);
				error.put("error", "Parameter ID not found or value missing");
				results.add(error);
			}
		}

		return ResponseEntity.ok(results);
	}

	@GetMapping("/getParameterDataForLineChart")
	public ResponseEntity<?> getParameterDataForLineChart(@RequestParam("deviceId") Long deviceId,
			@RequestParam("parameterId") List<String> parameterId, @RequestParam("sdate") String sdate,
			@RequestParam("edate") String edate, @RequestParam(value = "limit", required = false) Integer limit) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			Timestamp startDate = Timestamp.valueOf(LocalDateTime.parse(sdate, formatter));
			Timestamp endDate = Timestamp.valueOf(LocalDateTime.parse(edate, formatter));

			List<Map<String, Object>> result = genericApiService.getParameterDataForMultipleKeys(deviceId, startDate,
					endDate, parameterId, limit);
			return ResponseEntity.ok(result);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
		}
	}

	@RequestMapping(value = { "/GetDeviceDataByDevices/{user_id}/{deviceIds}" }, produces = { "application/json" })
	public String GetDeviceDataByDevices(@PathVariable long user_id, @PathVariable String deviceIds)
			throws ParseException, IOException {

		List<Long> deviceIdList = Arrays.stream(deviceIds.split(",")).map(String::trim).map(Long::parseLong)
				.collect(Collectors.toList());

		JSONObject obj1 = new JSONObject();
		JSONArray jarray1 = new JSONArray();
		JSONArray jarray3 = new JSONArray();

		JSONArray jcolumn4 = new JSONArray();
		jcolumn4.put("#");
		jarray1.put(jcolumn4);

		JSONArray jcolumn1 = new JSONArray();
		jcolumn1.put("Site Name");
		jarray1.put(jcolumn1);

		JSONArray jcolumn2 = new JSONArray();
		jcolumn2.put("Device Time");
		jarray1.put(jcolumn2);

		int flag = 0, flag2 = 0, flag3 = 0;
		int i = 1;

		for (Long deviceId : deviceIdList) {
			List<Object[]> list3 = this.hstServide.getdevicekeyvalbydid(deviceId);
			List<Object[]> list4 = this.hstServide.getdevicekeyvaldigitalbydid(deviceId);
			String deviceName = list3.isEmpty() ? "" : String.valueOf(list3.get(0)[2]);
			String deviceTime = hstServide.getDeviceDateByDeviceId(deviceId);

			JSONArray jarray2 = new JSONArray();
			jarray2.put(i++);
			jarray2.put(deviceName);
			jarray2.put(deviceTime);

			for (Object[] result2 : list3) {
				if (flag == 0) {
					Parameter prmnamelist = this.hstServide.findpOne(Long.parseLong(result2[0].toString()));
					JSONArray jcolumn = new JSONArray();

					String analogunit = "";
					try {
						List<Object[]> unitList = hstServide.getprofileanalogunit(deviceId, result2[0].toString());
						if (!unitList.isEmpty()) {
							Object[] row = unitList.get(0);
							analogunit = row[0] != null ? row[0].toString() : "";
						}
					} catch (Exception e) {
						log.error("Error fetching analog unit for input: " + result2[0], e);
					}

					jcolumn.put(prmnamelist.getPrmname() + " (" + analogunit + ")");
					jarray1.put(jcolumn);
				}
				jarray2.put(result2[1].toString().replaceAll("\"", ""));
			}

			for (Object[] result4 : list4) {
				if (flag2 == 0) {
					JSONArray jcolumnd = new JSONArray();
					Parameter prmnamelist = this.hstServide.findpOne(Long.parseLong(result4[0].toString()));
					jcolumnd.put(prmnamelist.getPrmname());
					jarray1.put(jcolumnd);
				}

				String value = result4[1].toString().replaceAll("\"", "");
				jarray2.put(value.equalsIgnoreCase("0") ? "OFF" : "ON");
			}

			jarray3.put(jarray2);
			flag++;
			flag2++;
		}

		if (flag3 == 0) {
			JSONArray jcolumn3 = new JSONArray();
			jcolumn3.put("Action");
			jarray1.put(jcolumn3);
			flag3++;
		}

		obj1.put("columns", jarray1);
		obj1.put("data", jarray3);

		return obj1.toString();
	}

	@RequestMapping(value = { "/GetDeviceDataByProfileForGenericDashBoard/{user_id}/{profileId}" }, produces = {
		"application/json" })
public String GetDeviceDataByProfileForGenericDashBoard(@PathVariable long user_id, @PathVariable long profileId)
		throws ParseException, IOException {

	List<Object[]> deviceList = this.hstServide.getdevicebyprid(profileId, user_id);

	JSONObject obj1 = new JSONObject();
	JSONArray columnsArray = new JSONArray();
	JSONArray dataArray = new JSONArray();

	JSONArray col0 = new JSONArray();
	col0.put("#");
	columnsArray.put(col0);

	JSONArray col = new JSONArray();
	col.put("deviceId");
	columnsArray.put(col);

	JSONArray col1 = new JSONArray();
	col1.put("Site Name");
	columnsArray.put(col1);

	JSONArray col2 = new JSONArray();
	col2.put("Device Time");
	columnsArray.put(col2);

	int analogHeaderFlag = 0;
	int digitalHeaderFlag = 0;

	int i = 1;
	for (Object[] dev : deviceList) {
		Long deviceId = Long.valueOf(dev[0].toString());

		List<Object[]> analogValues = this.hstServide.getdevicekeyvalbydid(deviceId);
		List<Object[]> digitalValues = this.hstServide.getdevicekeyvaldigitalbydid(deviceId);

		JSONArray rowArray = new JSONArray();
		rowArray.put(i++);
		rowArray.put(deviceId);
		rowArray.put(dev[1].toString());
		rowArray.put(dev[2].toString());

		for (Object[] result2 : analogValues) {
			if (analogHeaderFlag == 0) {
				Parameter prmnamelist = this.hstServide.findpOne(Long.parseLong(result2[0].toString()));

				List list6 = hstServide.getprofileanalogunit(profileId, result2[0].toString());
				String analogUnit = list6.isEmpty() ? "" : (String) list6.get(0);

				JSONArray colAnalog = new JSONArray();
				colAnalog.put(prmnamelist.getPrmname() + "(" + analogUnit + ")");
				columnsArray.put(colAnalog);
			}

			rowArray.put(result2[1].toString().replaceAll("\"", ""));
		}
		analogHeaderFlag++;

		for (Object[] result4 : digitalValues) {
			if (digitalHeaderFlag == 0) {
				JSONArray colDigital = new JSONArray();
				Parameter prmnamelist = this.hstServide.findpOne(Long.parseLong(result4[0].toString()));
				colDigital.put(prmnamelist.getPrmname());
				columnsArray.put(colDigital);
			}

			String val = result4[1].toString().replaceAll("\"", "");
			rowArray.put(val.equalsIgnoreCase("0") ? "OFF" : "ON");
		}
		digitalHeaderFlag++;

		dataArray.put(rowArray);
	}

	obj1.put("columns", columnsArray);
	obj1.put("data", dataArray);

	return obj1.toString();
}



@RequestMapping(value = "/getRecordForBarChart/{deviceId}/{paramId}/{startDate}/{endDate}/{type}", produces = {
		"application/json" })
public String getRecordForBarChart(@PathVariable Long deviceId, @PathVariable String paramId,
		@PathVariable String startDate, @PathVariable String endDate, @PathVariable String type)
		throws JsonGenerationException, JsonMappingException, IOException, ParseException {
	ObjectMapper mapper = new ObjectMapper();
	mapper.setDateFormat(df);
	JSONObject databject = new JSONObject();
	JSONArray dataArray = new JSONArray();
	JSONArray categoriesArray = new JSONArray();
	List<Object[]> deviceDataata = null;

	if (type.equalsIgnoreCase("DAY")) {
		deviceDataata = genericApiService.gerDailyBarChartData(deviceId, paramId, startDate, endDate);
		deviceDataata.forEach((Object[] o) -> {
			try {
				categoriesArray.put(reporteHourDf.format(sdf.parseObject(o[0].toString())));
				dataArray.put(o[5]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});

	}
	if (type.equalsIgnoreCase("MONTH")) {
		deviceDataata = genericApiService.gerMonthlyBarChartData(deviceId, paramId, startDate, endDate);
		deviceDataata.forEach((Object[] o) -> {
			try {
				categoriesArray.put(reporteDateDf.format(sdf.parseObject(o[0].toString())));
				dataArray.put(o[5]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
	}
	if (type.equalsIgnoreCase("YEAR")) {
		deviceDataata = genericApiService.gerYearChartData(deviceId, paramId, startDate, endDate);
		deviceDataata.forEach((Object[] o) -> {
			try {
				categoriesArray.put(reporteYearDf.format(sdf.parseObject(o[0].toString())));
				dataArray.put(o[5]);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		});
	}

	databject.put("categories", categoriesArray);
	databject.put("data", dataArray);
	databject.put("prmname", parameterService.get(Long.parseLong(paramId)).getPrmname());

	return databject.toString();
}

@RequestMapping(value = { "/GetRoleWiseProfile/{user_id}" }, produces = { "application/json" })
public String getRoleWiseProfile(@PathVariable("user_id") long userId) {
	JSONArray profiles = genericApiService.getRoleWiseProfile(userId);
	return profiles.toString();
}

@RequestMapping(value = { "/GetDevicesByProfile/{profile_id}" }, produces = { "application/json" })
public String getDevicesByProfile(@PathVariable("profile_id") long profileId) {
	JSONArray devices = genericApiService.getDevicesByProfileId(profileId);
	return devices.toString();
}


}
