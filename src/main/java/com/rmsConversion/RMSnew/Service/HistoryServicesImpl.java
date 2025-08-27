package com.rmsConversion.RMSnew.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.Parameter;
import com.rmsConversion.RMSnew.Repository.DevicemasterRepository;
import com.rmsConversion.RMSnew.Repository.HistoryRepository;
import com.rmsConversion.RMSnew.Repository.LasttrackRepository;
import com.rmsConversion.RMSnew.Repository.ParameterRepository;

@Service
public class HistoryServicesImpl implements HistoryServices {
    
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
	LasttrackRepository lastrackrepository;

    @Autowired
	ParameterRepository prepository;
    
    @Autowired
	DevicemasterRepository devicerepository;

    @Override
    public History save(History history) {
        return historyRepository.save(history);
    }
    
    @Override
    public List<History> getlist() {
        return historyRepository.findAll();
    }
    
    @Override
    public List<History> getHistoryByDeviceIdAndDateRange(Long deviceId, Date startDate, Date endDate) {
        return historyRepository.findByDeviceIdAndDateRange(deviceId, startDate, endDate);
    }

    public List<Object[]> getdevicekeyvalbydid(Long deviceid) {
		return this.lastrackrepository.getdevicekeyvalbydid(deviceid.longValue());
	}
    
    public List<Object[]> getdevicebyprid(Long prid, Long userid) {
		return this.devicerepository.getdevicebyprid(prid.longValue(), userid.longValue());
    }
     public List<Object[]> getdevicekeyvaldigitalbydid(Long deviceid) {
            return this.lastrackrepository.getdevicekeyvaldigitalbydid(deviceid.longValue());
     }	
     public String getDeviceDateByDeviceId(Long deviceId) {
		return lastrackrepository.findDeviceDateByDeviceId(deviceId);
     }
        public Parameter findpOne(Long id) {
            return this.prepository.findByid(id);
        }

        public List<Object[]> getprofileanalogunit(Long prid, String analoginput) {
            return this.historyRepository.getprofileanalogunit(prid, analoginput);
        }

        public List<Object[]> getdeviceHistoryLocation(Long deviceid, String startdate, String enddate, Long max) {
            return this.historyRepository.getdeviceHistoryLocation(deviceid, startdate, enddate, max);
        }

        public List<Object[]> getadminHistoryLocation(Long deviceid, String startdate, String enddate, Long max) {
            return this.historyRepository.getadminHistoryLocation(deviceid, startdate, enddate, max);
        }

        @Override
	public ResponseEntity<Map<String, Object>> getPlaybackData(int deviceId, String startDate, String endDate,
			String dataType) {
		List<Object[]> rawData;
		if ("no".equalsIgnoreCase(dataType)) {
			rawData = historyRepository.getNonZeroSpeedData(deviceId, startDate, endDate);
		} else {
			rawData = historyRepository.getAllSpeedData(deviceId, startDate, endDate);
		}

		List<Map<String, Object>> data = new ArrayList<>();
		for (Object[] row : rawData) {
			Map<String, Object> map = new HashMap<>();
			map.put("deviceId", row[0]);
			map.put("latitude", row[1]);
			map.put("longitude", row[2]);
			map.put("speed", row[3]);
			map.put("angle", row[4]);
			map.put("deviceDate", row[5]);
			data.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("data", data);
		response.put("count", data.size());
		return ResponseEntity.ok(response);
	}

    @Override
	public ResponseEntity<Map<String, Object>> getHistoryData(int deviceId, String startDate, String endDate) {
		List<Object[]> rawData = historyRepository.getHistoryData(deviceId, startDate, endDate);

		List<Map<String, Object>> data = new ArrayList<>();
		for (Object[] row : rawData) {
			Map<String, Object> map = new HashMap<>();
			map.put("deviceId", row[0]);
			map.put("latitude", row[1]);
			map.put("longitude", row[2]);
			map.put("speed", row[3]);
			map.put("angle", row[4]);
			map.put("deviceDate", row[5]);
			data.add(map);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("data", data);
		response.put("count", data.size());

		return ResponseEntity.ok(response);
	}

    public List<Map<String, Object>> getAllByDeviceIdAndDateRange(Long deviceId) {
		List<Object[]> rows = historyRepository.findAllByDeviceIdAndDateRange(deviceId);
		List<Map<String, Object>> result = new ArrayList<>();

		for (Object[] row : rows) {
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("deviceId", row[0]);
			map.put("deviceDate", row[1]);
			map.put("latitude", row[2]);
			map.put("longitude", row[3]);
			map.put("angle", row[4]);
			map.put("speed", row[5]);
			result.add(map);
		}

		return result;
	}

	}




