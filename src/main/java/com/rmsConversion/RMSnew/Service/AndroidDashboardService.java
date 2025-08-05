package com.rmsConversion.RMSnew.Service;

import com.rmsConversion.RMSnew.Model.AndroidDashboardMaster;

public interface AndroidDashboardService {

    AndroidDashboardMaster findByUserIdAndRole(Long userId, String role);

    AndroidDashboardMaster findByManagerIdAndRole(Long managerId, String role);

    AndroidDashboardMaster save(AndroidDashboardMaster adm);
} 