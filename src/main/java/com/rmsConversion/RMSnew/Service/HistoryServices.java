package com.rmsConversion.RMSnew.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.Parameter;

public interface HistoryServices {
    
    History save(History history);
    
    List<History> getlist();
    
    List<History> getHistoryByDeviceIdAndDateRange(Long deviceId, Date startDate, Date endDate);
    
    List<Object[]> getdevicekeyvalbydid(Long deviceid);

    List<Object[]> getdevicebyprid(Long prid, Long userid);

    List<Object[]> getdevicekeyvaldigitalbydid(Long deviceid);

    String getDeviceDateByDeviceId(Long deviceId);

    Parameter findpOne(Long id);

    List<Object[]> getprofileanalogunit(Long prid, String analoginput);

    List<Object[]> getdeviceHistoryLocation(Long deviceid, String startdate, String enddate, Long max);

    List<Object[]> getadminHistoryLocation(Long deviceid, String startdate, String enddate, Long max);

    ResponseEntity<Map<String, Object>> getPlaybackData(int deviceId, String startDate, String endDate,
    String dataType);

    ResponseEntity<Map<String, Object>> getHistoryData(int deviceId, String startDate, String endDate);

    List<Map<String, Object>> getAllByDeviceIdAndDateRange(Long deviceId);
}
