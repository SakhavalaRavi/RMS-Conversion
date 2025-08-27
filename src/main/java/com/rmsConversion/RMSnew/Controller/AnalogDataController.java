package com.rmsConversion.RMSnew.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rmsConversion.RMSnew.Model.Analogdata;
import com.rmsConversion.RMSnew.Model.BonrixUser;
import com.rmsConversion.RMSnew.Model.Dashboarddetails;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.AnalogDataServices;
import com.rmsConversion.RMSnew.Service.DevicemasterServices;

@CrossOrigin(origins = "*")
@Transactional
@RestController
@RequestMapping("/api")
public class AnalogDataController {

    @Autowired
	AnalogDataServices AnalogDataservice;

	@Autowired
	DevicemasterServices Deviceinfoservices;
	



    @RequestMapping(value = "/analogdata", produces = { "application/json" })
	public String getcomponetlist() throws JsonProcessingException {
		JSONArray arr_strJson = new JSONArray(AnalogDataservice.getlist());
		System.out.println(arr_strJson.toString());
		JSONObject tcallerWbJsom = new JSONObject();
		tcallerWbJsom.put("data", arr_strJson);
		return tcallerWbJsom.toString();
	}

	@RequestMapping(value = "/analogdata/{id}", method = RequestMethod.GET)
	public Analogdata getcomponet(@PathVariable int id) {
		return AnalogDataservice.get(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/analogdata/{id}")
	public String updatecomponet(@RequestBody Analogdata bs, @PathVariable int id) {
		bs.setId(id);
		return AnalogDataservice.update(bs);
	}

	@RequestMapping(value = "/AnalogDashboard", produces = { "application/json" })
	public List<Dashboarddetails> AnalogDashboard(Authentication auth) {
		BonrixUser currentUser = (BonrixUser) auth.getPrincipal();
		Set<UserRole> liss = currentUser.getUserRole();
		System.out.println(liss);
		System.out.println(currentUser.getUsername());// currentUser.getUserId()
		return null;
	}

	@RequestMapping(value = "/digitallist", produces = { "application/json" })
	public String getDigitaldatalist() {
		List<Object[]> list = new ArrayList<>();
		list = AnalogDataservice.getDigitaldatalist();
		JSONArray jarray = new JSONArray();

		String id = "";
		String prmname = "";
		String prmtype = "";
		for (Object[] result1 : list) {
			id = result1[0].toString();
			prmname = result1[1].toString();
			prmtype = result1[2].toString();

			JSONObject jo = new JSONObject();
			jo.put("id", id);
			jo.put("prmname", prmname);
			jo.put("prmtype", prmtype);

			jarray.put(jo);
		}
		return jarray.toString();
	}

	@RequestMapping(value = "/analoglist", produces = { "application/json" })
	public String getAnalogdatalist() {
		List<Object[]> list = new ArrayList<>();
		list = AnalogDataservice.getAnalogdatalist();
		JSONArray jarray = new JSONArray();

		String id = "";
		String prmname = "";
		String prmtype = "";
		for (Object[] result1 : list) {
			id = result1[0].toString();
			prmname = result1[1].toString();
			prmtype = result1[2].toString();

			JSONObject jo = new JSONObject();
			jo.put("id", id);
			jo.put("prmname", prmname);
			jo.put("prmtype", prmtype);

			jarray.put(jo);
		}
		return jarray.toString();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/analogdata/{did}/{managerid}")
	@ExceptionHandler({ SpringException.class })
	public String savedata(
        @RequestBody Analogdata bs,
        @PathVariable("did") Long did,
        @PathVariable("managerid") long managerid,
        @RequestHeader("Authorization") String authorizationHeader) {
    
   
    String token = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
    }

   
    if (token == null || token.isEmpty()) {
        return new SpringException(false, "Invalid or Missing Authorization Token").toString();
    }

    bs.setDevice(Deviceinfoservices.findOne(did));
    bs.setManagerid(managerid);
    AnalogDataservice.save(bs);

    return new SpringException(true, "Analog Data Successfully Added").toString();
}

@RequestMapping(method = RequestMethod.DELETE, value = "/analogdata/{id}")
public String deletecomponet(
        @PathVariable int id,
        @RequestHeader("Authorization") String authorizationHeader) {
    
   
    String token = null;
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
    }

    if (token == null || token.isEmpty()) {
        return new SpringException(false, "Invalid or Missing Authorization Token").toString();
    }

    return AnalogDataservice.delete(id);
}


}
