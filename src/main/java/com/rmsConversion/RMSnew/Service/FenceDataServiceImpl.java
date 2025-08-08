package com.rmsConversion.RMSnew.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rmsConversion.RMSnew.Model.FenceData;
import com.rmsConversion.RMSnew.Repository.FenceRepository;
@Service
@Transactional
public class FenceDataServiceImpl implements FenceDataService {

	@Autowired
	private FenceRepository fenceDataRepository;

	@Override
	public FenceData addFence(FenceData fenceData) {
		return fenceDataRepository.save(fenceData);
	}

	@Override
	public List<FenceData> getAllFencesByManagerId(Long managerId) {
		return fenceDataRepository.findAllByManagerId(managerId);
	}

	@Override
	public FenceData updateFence(Long id, Long managerId, FenceData updatedData) {
		FenceData existing = fenceDataRepository.findFenceByIdAndManagerId(id, managerId);
		if (existing == null) {
			return null;
		}
		existing.setFencename(updatedData.getFencename());
		existing.setFencetype(updatedData.getFencetype());
		existing.setFencevalue(updatedData.getFencevalue());
		existing.setStatus(updatedData.getStatus());
		return fenceDataRepository.save(existing);
	}

	@Override
	public boolean deleteFenceById(Long id, Long managerId) {
		int result = fenceDataRepository.deleteFenceByFenceidAndManagerId(id, managerId);
		return result > 0;
	}

	
	
} 