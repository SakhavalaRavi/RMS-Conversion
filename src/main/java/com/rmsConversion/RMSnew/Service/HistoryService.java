package com.rmsConversion.RMSnew.Service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.rmsConversion.RMSnew.Model.History;

public interface HistoryService {
    List<Object[]> getdeviceHistoryLocation(Long deviceid, String startdate, String enddate, Long max);
    List<Object[]> getadminHistoryLocation(Long deviceid, String startdate, String enddate, Long max);
    ResponseEntity<Map<String, Object>> getPlaybackData(int deviceId, String startDate, String endDate, String dataType);
    ResponseEntity<Map<String, Object>> getHistoryData(int deviceId, String startDate, String endDate);
    List<Map<String, Object>> getAllByDeviceIdAndDateRange(Long deviceId);
}
