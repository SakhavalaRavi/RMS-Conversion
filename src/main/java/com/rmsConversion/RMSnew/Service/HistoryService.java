package com.rmsConversion.RMSnew.Service;

import java.util.List;
import com.rmsConversion.RMSnew.Model.History;

public interface HistoryService {
    
    History save(History history);
    
    List<History> getlist();
    
}
