package com.rmsConversion.RMSnew.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rmsConversion.RMSnew.Model.DeviceProfile;
import com.rmsConversion.RMSnew.Repository.DeviceProfileRepository;

@Service
public class DeviceProfileServiceImpl implements DeviceProfileService {
    
    @Autowired
    private DeviceProfileRepository deviceProfileRepository;
    
    @Override
    public DeviceProfile save(DeviceProfile deviceProfile) {
        return deviceProfileRepository.save(deviceProfile);
    }
    
    @Override
    public List<DeviceProfile> getlist() {
        return deviceProfileRepository.findAll();
    }
    
} 