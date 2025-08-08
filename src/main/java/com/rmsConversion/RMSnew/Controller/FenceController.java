package com.rmsConversion.RMSnew.Controller;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.FenceData;
import com.rmsConversion.RMSnew.Service.FenceDataService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api")
public class FenceController {

	@Autowired
	private FenceDataService fenceDataService;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(FenceController.class);

	@PostMapping("/addFenceData/{fencename}/{fencevalue}/{managerId}")
	public FenceData addFence(@PathVariable String fencename, @PathVariable String fencevalue,
			@PathVariable Long managerId, @RequestBody(required = false) Map<String, Object> optionalData) {

		FenceData fenceData = new FenceData();
		fenceData.setManagerid(managerId);
		fenceData.setFencename(fencename);
		fenceData.setFencevalue(fencevalue);

		if (optionalData != null) {
			fenceData.setFencetype((String) optionalData.getOrDefault("fencetype", "circle"));
			fenceData.setStatus(optionalData.get("status") != null ? (Boolean) optionalData.get("status") : true);
		} else {
			fenceData.setFencetype("circle");
			fenceData.setStatus(true);
		}

		return fenceDataService.addFence(fenceData);
	}

	@GetMapping("/getAllFenceData/{managerId}")
	public List<FenceData> getAllFences(@PathVariable Long managerId) {
		return fenceDataService.getAllFencesByManagerId(managerId);
	}

	@PutMapping("/updateFenceData/{id}/{managerId}")
	public FenceData updateFence(@PathVariable Long id, @PathVariable Long managerId,
			@RequestBody FenceData updatedFence) {
		return fenceDataService.updateFence(id, managerId, updatedFence);
	}

	@DeleteMapping("/deleteFenceData/{id}/{managerId}")
	public String deleteFence(@PathVariable Long id, @PathVariable Long managerId) {
		boolean deleted = fenceDataService.deleteFenceById(id, managerId);
		if (deleted) {
			return "Fence with ID " + id + " has been deleted.";
		} else {
			return "Fence with ID " + id + " not found or unauthorized.";
		}
	}



} 