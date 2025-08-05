package com.rmsConversion.RMSnew.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.AndroidDashboardMaster;
import com.rmsConversion.RMSnew.Repository.AndroidDashboardRepository;

@Service
public class AndroidDashboardServiceImpl implements AndroidDashboardService {

    @Autowired
    private AndroidDashboardRepository androidDashboardRepository;

    @Override
    public AndroidDashboardMaster findByUserIdAndRole(Long userId, String role) {
        return androidDashboardRepository.findByUserIdAndRole(userId, role);
    }

    @Override
    public AndroidDashboardMaster findByManagerIdAndRole(Long managerId, String role) {
        return androidDashboardRepository.findByManagerIdAndRole(managerId, role);
    }

    @Override
    public AndroidDashboardMaster save(AndroidDashboardMaster adm) {
        return androidDashboardRepository.save(adm);
    }
} 