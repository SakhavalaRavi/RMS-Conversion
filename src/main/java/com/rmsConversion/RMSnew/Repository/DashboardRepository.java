package com.rmsConversion.RMSnew.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Dashboarddetails;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboarddetails, Long> {

  
}
