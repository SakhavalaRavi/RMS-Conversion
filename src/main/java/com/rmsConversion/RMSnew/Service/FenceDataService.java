package com.rmsConversion.RMSnew.Service;

import java.util.List;
import com.rmsConversion.RMSnew.Model.FenceData;

public interface FenceDataService {

	FenceData addFence(FenceData fenceData);

	List<FenceData> getAllFencesByManagerId(Long managerId);

	FenceData updateFence(Long id, Long managerId, FenceData updatedData);

	boolean deleteFenceById(Long id, Long managerId);
	
	
		      
} 