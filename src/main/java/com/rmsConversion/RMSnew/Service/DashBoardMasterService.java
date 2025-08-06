package com.rmsConversion.RMSnew.Service;

import java.util.List;

import com.rmsConversion.RMSnew.Model.DashboardMaster;
import com.rmsConversion.RMSnew.Model.MenuMst;

public interface DashBoardMasterService {

	void newMenu(DashboardMaster mst);
	
	String getMenuUrl(long mid);
	
	List<Object[]> getAllocatedMenuByUserId(long Id);
	
	List<Object[]> getAllocatedMenuByManagerId(long Id);
	
	public DashboardMaster findByUserIdAndRole(Long userId, String role);

	public DashboardMaster findByManagerIdAndRole(Long managerId, String role);
	
} 