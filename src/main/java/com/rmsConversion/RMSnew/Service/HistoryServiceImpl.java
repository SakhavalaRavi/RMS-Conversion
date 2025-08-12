package com.rmsConversion.RMSnew.Service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
    
    @Autowired
    private HistoryRepository historyRepository;
    
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
    
}
