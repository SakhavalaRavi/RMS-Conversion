package com.rmsConversion.RMSnew.Service;

import java.util.List;
import com.rmsConversion.RMSnew.Model.DeviceProfile;

public interface DeviceProfileService {
    
    DeviceProfile save(DeviceProfile deviceProfile);
    
    List<DeviceProfile> getlist();
    
} 