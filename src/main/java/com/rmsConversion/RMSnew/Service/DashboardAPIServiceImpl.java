package com.rmsConversion.RMSnew.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Repository.DashboardAPIRepository;

@Service
public class DashboardAPIServiceImpl implements DashboardAPIService {
    

    @Autowired
	DashboardAPIRepository repo;

    @Override
	public List<Object[]> GetDeviceParameterRecords(Long deviceId, String prmname, int limit) {
		if (limit <= 0) {
			return repo.GetAllDeviceParameterRecords(deviceId, prmname);
		}
		return repo.GetDeviceParameterRecords(deviceId, prmname, limit);
	}
    
}
