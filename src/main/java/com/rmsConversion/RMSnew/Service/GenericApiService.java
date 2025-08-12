package com.rmsConversion.RMSnew.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
}
