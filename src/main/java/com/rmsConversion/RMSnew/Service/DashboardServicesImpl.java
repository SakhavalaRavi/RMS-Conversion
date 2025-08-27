package com.rmsConversion.RMSnew.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.Dashboarddetails;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Repository.DashboardRepository;



@Service
public class DashboardServicesImpl implements DashboardServices {
    
    @Autowired
	DashboardRepository repository;

    @Override
	public void save(Dashboarddetails dd) {
		repository.save(dd);
		
	}

	@Override
	public List<Dashboarddetails> getlist() {
		return repository.findAll();
	}

	@Override
	public Dashboarddetails get(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public String update(Dashboarddetails dd) {
		 repository.saveAndFlush(dd);
		 return new SpringException(true, "Dashboard sucessfully Updated").toString();
	}

	@Override
	public String delete(Long id) {
		repository.deleteById(id);
		 return new SpringException(true, "Dashboard sucessfully Deletaed").toString();
	}

}
