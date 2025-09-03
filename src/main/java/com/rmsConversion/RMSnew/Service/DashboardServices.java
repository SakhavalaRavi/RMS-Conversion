package com.rmsConversion.RMSnew.Service;

import java.util.List;

import com.rmsConversion.RMSnew.Model.Dashboarddetails;

public interface DashboardServices {
    
    void save(Dashboarddetails dd);

	List<Dashboarddetails> getlist();

	Dashboarddetails get(Long id);

	String update(Dashboarddetails dd);

	String delete(Long id);
}
