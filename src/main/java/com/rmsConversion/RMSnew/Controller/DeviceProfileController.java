package com.rmsConversion.RMSnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.rmsConversion.RMSnew.Model.DeviceProfile;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.DeviceProfileService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class DeviceProfileController {
    
   
    @Autowired
    private DeviceProfileService deviceProfileService;
    
    @RequestMapping(method=RequestMethod.POST,value="/deviceprofile", produces={"application/json"})
    public String savedata(@RequestBody DeviceProfile bs)
    { 
        deviceProfileService.save(bs);
        return new SpringException(true, "Componet Sucessfully Added").toString();
    }
    
    @RequestMapping(value="/deviceprofile", produces={"application/json"})
    public List<DeviceProfile> getdeviceprofilelist()
    {
         return deviceProfileService.getlist();
    }
    
} 