package com.rmsConversion.RMSnew.Service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.Parameter;
import com.rmsConversion.RMSnew.Repository.ParameterRepository;



@Service
public class ParameterServicesImpl implements ParameterServices {
    
    @Autowired
	ParameterRepository repository;

    public Parameter get(Long id) {
		return repository.findById(id).orElse(null);
	}
   
    
}
