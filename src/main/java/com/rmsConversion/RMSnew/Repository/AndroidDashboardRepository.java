package com.rmsConversion.RMSnew.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.AndroidDashboardMaster;

@Repository
public interface AndroidDashboardRepository extends JpaRepository<AndroidDashboardMaster, Long> {

    AndroidDashboardMaster findByUserIdAndRole(Long userId, String role);

    AndroidDashboardMaster findByManagerIdAndRole(Long managerId, String role);

    @Query(value = "SELECT mid FROM android_dashboard_master WHERE (:role = 'ROLE_USER' AND user_id = :id) OR (:role = 'ROLE_MANAGER' AND manager_id = :id) LIMIT 1", nativeQuery = true)
    String getDefaultMenu(@Param("id") Long id, @Param("role") String role);

} 