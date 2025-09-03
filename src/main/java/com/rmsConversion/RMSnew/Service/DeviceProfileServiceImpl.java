package com.rmsConversion.RMSnew.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rmsConversion.RMSnew.Model.DeviceProfile;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Repository.DeviceProfileRepository;

@Service
public class DeviceProfileServiceImpl implements DeviceProfileService {
    
    @Autowired
    private DeviceProfileRepository deviceProfileRepository;
    
    @Override
    public DeviceProfile save(DeviceProfile bs) {
        return deviceProfileRepository.save(bs);
    }
    
    @Override
    public List<DeviceProfile> getlist() {
        return deviceProfileRepository.findAll();
    }

    public DeviceProfile get(Long id) {
		return deviceProfileRepository.findById(id).orElse(null);
	}

    public List<DeviceProfile> getDeviceProfilesByIds(List<Long> ids) {
	    return deviceProfileRepository.getByPridList(ids);
	}

    public String update(DeviceProfile bs) {
		deviceProfileRepository.saveAndFlush(bs);
		return new SpringException(true, "Componet sucessfully Updated").toString();
	}
    
} 