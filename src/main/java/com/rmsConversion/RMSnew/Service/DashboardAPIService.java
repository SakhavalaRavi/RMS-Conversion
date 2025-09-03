package com.rmsConversion.RMSnew.Service;

import java.util.List;

public interface DashboardAPIService {
    
    List<Object[]> GetDeviceParameterRecords(Long deviceId, String prmname, int limit);
    
}
