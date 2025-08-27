package com.rmsConversion.RMSnew.Controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmsConversion.RMSnew.Model.DeviceProfile;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.DeviceProfileService;
import com.rmsConversion.RMSnew.Service.ParameterServices;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@CrossOrigin(origins = "*")
@RestController
@Transactional
@RequestMapping("/api")
public class DeviceProfileController {
    
   
    @Autowired
    DeviceProfileService deviceProfileService;

    @Autowired
	ParameterServices Parameterservices;
    
	private static final Logger logger = LoggerFactory.getLogger(DeviceProfileController.class);



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


    @RequestMapping(value="/deviceprofile/{id}",method=RequestMethod.GET)
    public DeviceProfile getdeviceprofile(@PathVariable("id") Long id) {
        return deviceProfileService.get(id);
    }

    @RequestMapping(value = "/multipalDeviceProfiles", method = RequestMethod.POST)
    public List<DeviceProfile> getDeviceProfiles(@RequestBody List<Long> ids) {
        return deviceProfileService.getDeviceProfilesByIds(ids);
    }

    @SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST, value = "/savedeviceprofile",produces={"application/json"})
		@ExceptionHandler(SpringException.class)
		public @ResponseBody ModelAndView savedeviceprofile(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws JsonParseException, JsonMappingException, IOException {
			String[] unit = request.getParameterValues("unit[]");
			String[] formula = request.getParameterValues("formula[]");
			String[] analogdata = request.getParameterValues("analogdata[]");
			String[] digitaldata = request.getParameterValues("digitaldata[]");
			String[] dreverse = request.getParameterValues("dreverse[]");
			String[] rs232data = request.getParameterValues("rs232data[]");
			String[] rs232unit = request.getParameterValues("rs232unit[]");
			String[] analogioindex = request.getParameterValues("analogioindex[]");
			String[] dioindex = request.getParameterValues("dioindex[]");
			String[] rs232ioindex = request.getParameterValues("rs232ioindex[]");
			
			// log.info("Analog length"+analogdata.length);
			// log.info("Digi length"+digitaldata.length);
			// log.info("RS232 length"+rs232data.length);
			 
			 JSONObject jo = new JSONObject();
			 
			 JSONArray analogjsonarr = new JSONArray();
			 for(int i=0;i<analogdata.length;i++){
			  // log.info("Analog unit"+unit[i]);
	         //  log.info("Analog formula"+formula[i]);
	         //  log.info("Analog analogdata"+analogdata[i]);
	           
	           JSONObject analogjo = new JSONObject();
	           analogjo.put("Analoginput", analogdata[i]);
	           analogjo.put("Analogunit", unit[i]);
	           analogjo.put("Analogformula", formula[i]);
	           analogjo.put("analogioindex", Integer.parseInt(analogioindex[i]));
	           String profilename=Parameterservices.get(Long.parseLong(analogdata[i])).getPrmname();
	         //  log.info("profilename:::  "+profilename);
	           analogjo.put("analogname", profilename);
	           analogjsonarr.put(analogjo);
	         }
			 	jo.put("Analog", analogjsonarr);
			 
			 
			 JSONArray digitaljsonarr = new JSONArray();
			 for(int i=0;i<digitaldata.length;i++){
		          // log.info("digital dreverse"+dreverse[i]);
		       //    log.info("digital digitaldata"+digitaldata[i]);
		           String digidata[]= digitaldata[i].split("#");
		           JSONObject digitaljo = new JSONObject();
		           digitaljo.put("reverse", Boolean.parseBoolean(dreverse[i]));
		           digitaljo.put("parameterId", digidata[0]);
		           digitaljo.put("parametername", digidata[1]);
		           digitaljo.put("dioindex",Integer.parseInt(dioindex[i]));
		           digitaljsonarr.put(digitaljo);
		        }
			    jo.put("Digital", digitaljsonarr);
			  
			  JSONArray rs232jsonarr = new JSONArray();
			  for(int i=0;i<rs232data.length;i++){
				  
		          // log.info("rs232 rs232data"+rs232data[i]);
		          // log.info("rs232 rsreverse "+rsreverse[i]);
		           
		           JSONObject rs232jo = new JSONObject();
		           String rs232dataa[]= rs232data[i].split("#");
		           rs232jo.put("rs232unit", rs232unit[i]);
		           rs232jo.put("parameterId", rs232dataa[0]);
		           rs232jo.put("parametername", rs232dataa[1]);
		           rs232jo.put("rs232ioindex", Integer.parseInt(rs232ioindex[i]));
		           rs232jsonarr.put(rs232jo);
		        }
			  jo.put("Rs232", rs232jsonarr);
			  
			  
			  String devicename = request.getParameter("devicename");
			  String gpsdata="{}";
			  Map<String, Object> parameters = new ObjectMapper().readValue(jo.toString(), HashMap.class);
			  Map<String, Object> gpsdataaa = new ObjectMapper().readValue(gpsdata, Map.class);
			  DeviceProfile dp= new DeviceProfile();
			  dp.setParameters(parameters);
			  dp.setProfilename(devicename);
			  dp.setGpsdata(gpsdataaa);
			  deviceProfileService.save(dp);
			  model.addAttribute("jsonresponse","DeviceProfile Sucessfully saved");
		        return new ModelAndView("redirect:/welcome#/deviceprofilelist", model);
		}


        //new code with min and max
		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST, value = "/saveDeviceProfileNew", produces = {"application/json"})
		@ExceptionHandler(SpringException.class)
		public @ResponseBody ModelAndView saveDeviceProfileNew(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JsonParseException, JsonMappingException, IOException {

		    String[] unit = request.getParameterValues("unit[]");
		    String[] formula = request.getParameterValues("formula[]");
		    String[] analogdata = request.getParameterValues("analogdata[]");
		    String[] digitaldata = request.getParameterValues("digitaldata[]");
		    String[] dreverse = request.getParameterValues("dreverse[]");
		    String[] analogioindex = request.getParameterValues("analogioindex[]");
		    String[] dioindex = request.getParameterValues("dioindex[]");
		    String[] min = request.getParameterValues("min[]");
		    String[] max = request.getParameterValues("max[]");
		    String[] cumulative = request.getParameterValues("cumulative[]");

		    JSONObject jo = new JSONObject();

		    JSONArray analogjsonarr = new JSONArray();
		    for (int i = 0; i < analogdata.length; i++) {
		        JSONObject analogjo = new JSONObject();
		        analogjo.put("Analoginput", analogdata[i]);
		        analogjo.put("Analogunit", unit[i]);
		        analogjo.put("Analogformula", formula[i]);
		        analogjo.put("analogioindex", Integer.parseInt(analogioindex[i]));
		        analogjo.put("min", (min != null && i < min.length) ? min[i] : "0");
		        analogjo.put("max", (max != null && i < max.length) ? max[i] : "0");
		        analogjo.put("cumulative", (cumulative != null && i < cumulative.length) && Boolean.parseBoolean(cumulative[i]));

		        String profilename = Parameterservices.get(Long.parseLong(analogdata[i])).getPrmname();
		        analogjo.put("analogname", profilename);

		        analogjsonarr.put(analogjo);
		    }
		    jo.put("Analog", analogjsonarr);

		    JSONArray digitaljsonarr = new JSONArray();
		    for (int i = 0; i < digitaldata.length; i++) {
		        String digidata[] = digitaldata[i].split("#");
		        JSONObject digitaljo = new JSONObject();
		        digitaljo.put("reverse", Boolean.parseBoolean(dreverse[i]));
		        digitaljo.put("parameterId", digidata[0]);
		        digitaljo.put("parametername", digidata[1]);
		        digitaljo.put("dioindex", Integer.parseInt(dioindex[i]));
		        digitaljsonarr.put(digitaljo);
		    }
		    jo.put("Digital", digitaljsonarr);


		    String devicename = request.getParameter("devicename");
		    String gpsdata = "{}";
		    Map<String, Object> parameters = new ObjectMapper().readValue(jo.toString(), HashMap.class);
		    Map<String, Object> gpsdataaa = new ObjectMapper().readValue(gpsdata, Map.class);

		    DeviceProfile dp = new DeviceProfile();
		    dp.setParameters(parameters);
		    dp.setProfilename(devicename);
		    dp.setGpsdata(gpsdataaa);

		    deviceProfileService.save(dp);

		    model.addAttribute("jsonresponse", "DeviceProfile Sucessfully saved");
		    return new ModelAndView("redirect:/welcome#/deviceprofilelist", model);
		}

		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST, value = "/updatedeviceprofile",produces={"application/json"})
		public @ResponseBody String updatedeviceprofile(HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		 
			String[] unit = request.getParameterValues("unit[]");
			String[] formula = request.getParameterValues("formula[]");
			String[] analogdata = request.getParameterValues("analogdata[]");
			String[] digitaldata = request.getParameterValues("digitaldata[]");
			String[] dreverse = request.getParameterValues("dreverse[]");
			String[] rs232data = request.getParameterValues("rs232data[]");
			String[] rsreverse = request.getParameterValues("rsreverse[]");
			int pid=Integer.parseInt(request.getParameter("d_id"));
			 
			 JSONObject jo = new JSONObject();
			 
			 JSONArray analogjsonarr = new JSONArray();
			 for(int i=0;i<analogdata.length;i++){
			  
	           
	           JSONObject analogjo = new JSONObject();
	           analogjo.put("Analoginput", analogdata[i]);
	           String profilename=Parameterservices.get(Long.parseLong(analogdata[i])).getPrmname();
	           analogjo.put("analogname", profilename);
	           analogjo.put("Analogunit", unit[i]);
	           analogjo.put("Analogformula", formula[i]);
	          
	           analogjsonarr.put(analogjo);
	         }
			 	jo.put("Analog", analogjsonarr);
			 
			 
			 JSONArray digitaljsonarr = new JSONArray();
			 for(int i=0;i<digitaldata.length;i++){
				  
		          
		           
		           JSONObject digitaljo = new JSONObject();
		           digitaljo.put("digitaldreverse", dreverse[i]);
		           digitaljo.put("digitaldata", digitaldata[i]);
		           digitaljsonarr.put(digitaljo);
		        }
			    jo.put("Digital", digitaljsonarr);
			  
			  JSONArray rs232jsonarr = new JSONArray();
			  for(int i=0;i<rs232data.length;i++){
				  
		         
		           
		           JSONObject rs232jo = new JSONObject();
		           rs232jo.put("rs232data", rs232data[i]);
		           rs232jo.put("rsreverse", rsreverse[i]);
		           rs232jsonarr.put(rs232jo);
		        }
			  jo.put("Rs232", rs232jsonarr);
			  
			  
			  String devicename = request.getParameter("devicename");
			  String gpsdata="{}";
			  Map<String, Object> parameters = new ObjectMapper().readValue(jo.toString(), Map.class);
			  Map<String, Object> gpsdataaa = new ObjectMapper().readValue(gpsdata, Map.class);
			  Long l = new Long(pid);
			  if(l!=0) {
				  DeviceProfile dp= new DeviceProfile();
				  dp.setPrid(l);
				  dp.setParameters(parameters);
				  dp.setProfilename(devicename);
				  dp.setGpsdata(gpsdataaa);
				  return deviceProfileService.update(dp); 
			  }else {
				  return "noo.....";
			  }
			 
		}

        //new update code
		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST, value = "/updateDeviceProfileNew", produces = {"application/json"})
		public @ResponseBody String updateDeviceProfileNew(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {


		    String[] unit = request.getParameterValues("unit[]");
		    String[] formula = request.getParameterValues("formula[]");
		    String[] analogdata = request.getParameterValues("analogdata[]");
		    String[] digitaldata = request.getParameterValues("digitaldata[]");
		    String[] dreverse = request.getParameterValues("dreverse[]");
		    String[] analogioindex = request.getParameterValues("analogioindex[]");
		    String[] min = request.getParameterValues("min[]");
		    String[] max = request.getParameterValues("max[]");
		    String[] cumulative = request.getParameterValues("cumulative[]");
		    String[] dioindex = request.getParameterValues("dioindex[]");

		    int pid = Integer.parseInt(request.getParameter("d_id"));

		    JSONObject jo = new JSONObject();
		    JSONArray analogjsonarr = new JSONArray();
		
		    for (int i = 0; i < analogdata.length; i++) {
		      

		        JSONObject analogjo = new JSONObject();
		        analogjo.put("Analoginput", analogdata[i]);
		        analogjo.put("Analogunit", unit[i]);
		        analogjo.put("Analogformula", formula[i]);
		        analogjo.put("analogioindex", Integer.parseInt(analogioindex[i]));
		        analogjo.put("min", (min != null && i < min.length) ? min[i] : "0");
		        analogjo.put("max", (max != null && i < max.length) ? max[i] : "0");
		        analogjo.put("cumulative", (cumulative != null && i < cumulative.length) && Boolean.parseBoolean(cumulative[i]));

		        String profilename = Parameterservices.get(Long.parseLong(analogdata[i])).getPrmname();
		        analogjo.put("analogname", profilename);

		        analogjsonarr.put(analogjo);
		    }
		    jo.put("Analog", analogjsonarr);

		    JSONArray digitaljsonarr = new JSONArray();
		    for (int i = 0; i < digitaldata.length; i++) {
		      

		        JSONObject digitaljo = new JSONObject();
		        String digidata[] = digitaldata[i].split("#");
		        digitaljo.put("reverse", Boolean.parseBoolean(dreverse[i]));
		        digitaljo.put("parameterId", digidata[0]);
		        digitaljo.put("parametername", digidata[1]);
		        digitaljo.put("dioindex", Integer.parseInt(dioindex[i]));
		        digitaljsonarr.put(digitaljo);
		    }
		    jo.put("Digital", digitaljsonarr);


		    String devicename = request.getParameter("devicename");
		    String gpsdata = "{}";

		    Map<String, Object> parameters = new ObjectMapper().readValue(jo.toString(), Map.class);
		    Map<String, Object> gpsdataaa = new ObjectMapper().readValue(gpsdata, Map.class);
		    Long l = Long.valueOf(pid);

		    if (l != 0) {
		        DeviceProfile dp = new DeviceProfile();
		        dp.setPrid(l);
		        dp.setParameters(parameters);
		        dp.setProfilename(devicename);
		        dp.setGpsdata(gpsdataaa);
		        return deviceProfileService.update(dp);
		    } else {
		        return "Invalid, try again...";
		    }
		}

		@SuppressWarnings("unchecked")
		@RequestMapping(method = RequestMethod.POST, value = "/savedeviceprofile1/{anunit}/{aninput}/{anindex}/{anformula}/{dginput}/{dgreverse}/{dgindex}/{gpsindex}/{gpsinput}/{prname}", produces = {
		"application/json" })
		@ExceptionHandler(SpringException.class)
		public void savedeviceprofile(ModelMap model, @PathVariable("anunit") String[] anunit,
		@PathVariable("aninput") String[] aninput, @PathVariable("anindex") String[] anindex,
		@PathVariable("anformula") String[] anformula, @PathVariable("dginput") String[] dginput,
		@PathVariable("dgreverse") String[] dgreverse, @PathVariable("dgindex") String[] dgindex,
		@PathVariable("gpsindex") String gpsindex, @PathVariable("gpsinput") String gpsinput,@PathVariable("prname") String prname)
		throws JsonParseException, JsonMappingException, IOException {

        String[] unit = anunit;
		String[] formula = anformula;
		String[] analogioindex = anindex;
		String[] analoginput = aninput;
		String[] digitaldata = dginput;
		String[] dreverse = dgreverse;
		String[] dioindex = dgindex;
		String gpsinput1 = gpsinput;
		String gpsindex1 = gpsindex;

		JSONObject jo = new JSONObject();

		JSONArray analogjsonarr = new JSONArray();
		for (int i = 0; i < analoginput.length; i++) {
		if(analoginput[i].toString().equalsIgnoreCase("NA"))
		break;
		else  
		{
		JSONObject analogjo = new JSONObject();
		analogjo.put("Analoginput", analogioindex[i]);
		analogjo.put("Analogunit", unit[i]);
		analogjo.put("Analogformula", formula[i]);
		analogjo.put("analogioindex", Integer.parseInt(analogioindex[i]));
		String profilename = Parameterservices.get(Long.parseLong(analoginput[i])).getPrmname();
		analogjo.put("analogname", profilename);
		analogjsonarr.put(analogjo);
		}
		}
		jo.put("Analog", analogjsonarr);

		JSONArray digitaljsonarr = new JSONArray();
		for (int i = 0; i < digitaldata.length; i++) {
		if(digitaldata[i].toString().equalsIgnoreCase("NA"))
		break;
		else
		{
		String profilename = Parameterservices.get(Long.parseLong(dginput[i])).getPrmname();
		JSONObject digitaljo = new JSONObject();
		digitaljo.put("reverse", Boolean.parseBoolean(dgreverse[i]));
		digitaljo.put("parameterId", dginput[i]);
		digitaljo.put("parametername", profilename);
		digitaljo.put("dioindex", Integer.parseInt(dgindex[i]));
		digitaljsonarr.put(digitaljo);
		}
		}
		jo.put("Digital", digitaljsonarr);

		JSONObject jo1 = new JSONObject();
		JSONArray gpsarray = new JSONArray();
		if(gpsinput1.equalsIgnoreCase("NA")) {
		gpsinput1="NA";
		gpsindex1="NA";
		}else {

		JSONObject gpsjo = new JSONObject();
		gpsjo.put("Gpsinput", gpsinput1.toString());
		gpsjo.put("Gpsindex",gpsindex1.toString());
		gpsarray.put(gpsjo);
		}
		jo1.put("Gps", gpsarray);
		System.out.println("Real  : " + jo.toString()); // Ok

		Map<String, Object> parameters = new ObjectMapper().readValue(jo.toString(), HashMap.class);
		Map<String, Object> gpsdataaa = new ObjectMapper().readValue(jo1.toString(), Map.class);
		DeviceProfile dp = new DeviceProfile();
		dp.setParameters(parameters);
		dp.setGpsdata(gpsdataaa);
		dp.setProfilename(prname);
		deviceProfileService.save(dp);
		}
		
    
} 