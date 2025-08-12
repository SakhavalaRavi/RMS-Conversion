package com.rmsConversion.RMSnew.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GapAnalysisService {
    
    List<Map<String, Object>> getGapReport(Long deviceId, Date startDate, Date endDate, long intervalInSeconds);
    
}
