package com.rmsConversion.RMSnew.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmsConversion.RMSnew.Model.DeviceProfile;
import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.Parameter;
import com.rmsConversion.RMSnew.Repository.DeviceProfileRepository;
import com.rmsConversion.RMSnew.Repository.HistoryRepository;

@Service
public class GenericApiServiceImpl implements GenericApiService {

	@Autowired
	private DeviceProfileRepository deviceProfileRepository;

	@Autowired
	private HistoryRepository historyRepository;

	

	
	@Override
	public List<Parameter> getParametersByIds(List<Long> ids) {
		return genericApiRepository.findByIdIn(ids);
	}

}
