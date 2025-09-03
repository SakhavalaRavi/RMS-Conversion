package com.rmsConversion.RMSnew.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.BonrixUser;
import com.rmsConversion.RMSnew.Model.Dashboarddetails;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.DashboardServices;

import jakarta.transaction.Transactional;

@CrossOrigin(origins = { "*" })
@RestController
@Transactional
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
	DashboardServices Dashboardservices;

    @RequestMapping(method = RequestMethod.POST, value = "/Dashboard")
	@ExceptionHandler(SpringException.class)
	public String savedata(@RequestBody Dashboarddetails dd,
			Authentication auth) {
		BonrixUser currentUser = (BonrixUser) auth.getPrincipal();
		Set<UserRole> liss= currentUser.getUserRole();
			System.out.println(liss);
		if(currentUser.getUserRole().stream().anyMatch(u->u.getRole().equalsIgnoreCase("ROLE_ADMIN")))
		{
		Dashboardservices.save(dd);
		return new SpringException(true, "Dashboard Sucessfully Added").toString();
		}else {
			return new SpringException(false, "Unthorized Access").toString();
		}
		
	}

    @RequestMapping(value="/Dashboard" ,produces={"application/json"})
		public List<Dashboarddetails> getDashboardlist(Authentication auth)
		{
			BonrixUser currentUser = (BonrixUser) auth.getPrincipal();
			Set<UserRole> liss= currentUser.getUserRole();
				System.out.println(liss);
			if(currentUser.getUserRole().stream().anyMatch(u->u.getRole().equalsIgnoreCase("ROLE_ADMIN")))
			{
			return Dashboardservices.getlist();
			}else {
			return null;
			}
		}

        @RequestMapping(value="/Dashboard/{id}",method=RequestMethod.GET)
		public Dashboarddetails getDashboard(@PathVariable long id) {
			return Dashboardservices.get(id);
		}
		@RequestMapping(method=RequestMethod.PUT,value="/Dashboard/{id}")
		public String updateDashboard(@RequestBody Dashboarddetails dd,@PathVariable long id)
		{
			dd.setId(id);
			return Dashboardservices.update(dd);
		}
		
		@RequestMapping(method=RequestMethod.DELETE,value="/Dashboard/{id}")
		public String deleteDashboard(@PathVariable long id)
		{
			return Dashboardservices.delete(id);
		}
}
