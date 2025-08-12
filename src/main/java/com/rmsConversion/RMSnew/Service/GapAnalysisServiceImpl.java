package com.rmsConversion.RMSnew.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Repository.HistoryRepository;
import java.text.SimpleDateFormat;

@Service
public class GapAnalysisServiceImpl implements GapAnalysisService {

	@Autowired
	private HistoryRepository historyrepo;
	
	@Override
	public List<Map<String, Object>> getGapReport(Long deviceId, Date startDate, Date endDate, long intervalSeconds) {
		List<Object[]> rawData = historyrepo.findDeviceDataInRange(deviceId, startDate, endDate);
		List<Map<String, Object>> report = new ArrayList<>();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		for (int i = 1; i < rawData.size(); i++) {
			Long dbDeviceId = null;
			if (rawData.get(i - 1)[0] instanceof Number) {
				dbDeviceId = ((Number) rawData.get(i - 1)[0]).longValue();
			}
			
			Date deviceDate1 = null;
			Date deviceDate2 = null;
			Date systemDate1 = null;
			Date systemDate2 = null;
			
			if (rawData.get(i - 1)[1] instanceof Date) {
				deviceDate1 = (Date) rawData.get(i - 1)[1];
				deviceDate2 = (Date) rawData.get(i)[1];
			} else if (rawData.get(i - 1)[1] instanceof Timestamp) {
				deviceDate1 = new Date(((Timestamp) rawData.get(i - 1)[1]).getTime());
				deviceDate2 = new Date(((Timestamp) rawData.get(i)[1]).getTime());
			}
			
			if (rawData.get(i - 1)[2] instanceof Date) {
				systemDate1 = (Date) rawData.get(i - 1)[2];
				systemDate2 = (Date) rawData.get(i)[2];
			} else if (rawData.get(i - 1)[2] instanceof Timestamp) {
				systemDate1 = new Date(((Timestamp) rawData.get(i - 1)[2]).getTime());
				systemDate2 = new Date(((Timestamp) rawData.get(i)[2]).getTime());
			}
			
			if (deviceDate1 != null && deviceDate2 != null) {
				long diff = (deviceDate2.getTime() - deviceDate1.getTime()) / 1000;
				
				if (diff > intervalSeconds) {
					Map<String, Object> row = new HashMap<>();
					row.put("deviceId", dbDeviceId);
					
					row.put("deviceDate1", dateFormatter.format(deviceDate1));
					row.put("deviceDate2", dateFormatter.format(deviceDate2));
					row.put("systemDate1", systemDate1 != null ? dateFormatter.format(systemDate1) : null);
					row.put("systemDate2", systemDate2 != null ? dateFormatter.format(systemDate2) : null);
					
					row.put("gapInSeconds", diff);
					
					report.add(row);
				}
			}
		}
		return report;
	}
}
