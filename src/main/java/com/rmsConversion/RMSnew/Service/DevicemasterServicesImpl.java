package com.rmsConversion.RMSnew.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.Devicemaster;
import com.rmsConversion.RMSnew.Repository.DevicemasterRepository;

@Service
public class DevicemasterServicesImpl implements DevicemasterServices {
    
    @Autowired
    DevicemasterRepository repository;

    
  public Devicemaster findOne(Long id) {
    return (Devicemaster)this.repository.findById(id).orElse(null);
  }
    
}
