package com.rmsConversion.RMSnew.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.Parameter;

public interface GenericApiService {

	List<Parameter> getParametersByIds(List<Long> ids);
	
	Map<String, Object> getParameterIdByNameForGenericDashboard(Long prid, String name);

	Long getProfileIdByProfileName(String profileName);

	List<Map<String, Object>> getParameterIdsByNames(Long prid, List<String> names);

	List<Map<String, Object>> getParametersByDeviceId(Long deviceId);

	List<Map<String, Object>> getParametersByDeviceIdMinAndMax(Long deviceId);

	Map<String, Object> getParameterWithId(Long deviceId, String analogKey);

	List<Map<String, Object>> getParameterDataForMultipleKeys(Long deviceId, Timestamp sdate, Timestamp edate,
			List<String> parameterId, Integer limit);

			List<Object[]> gerMonthlyBarChartData(long deviceId, String paramId, String startDate, String endDate);

			List<Object[]> gerDailyBarChartData(long deviceId, String paramId, String startDate, String endDate);

			List<Object[]> gerYearChartData(long deviceId, String paramId, String startDate, String endDate);

			JSONArray getDevicesByProfileId(long profileId);

			JSONArray getRoleWiseProfile(long userId);
}
