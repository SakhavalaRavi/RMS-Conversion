package com.rmsConversion.RMSnew.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
    
    @Autowired
    private HistoryRepository historyRepository;
    
    @Override
    public List<Object[]> getdeviceHistoryLocation(Long deviceid, String startdate, String enddate, Long max) {
        return historyRepository.getdeviceHistoryLocation(deviceid, startdate, enddate, max);
    }

    @Override
    public List<Object[]> getadminHistoryLocation(Long deviceid, String startdate, String enddate, Long max) {
        return historyRepository.getadminHistoryLocation(deviceid, startdate, enddate, max);
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
}
