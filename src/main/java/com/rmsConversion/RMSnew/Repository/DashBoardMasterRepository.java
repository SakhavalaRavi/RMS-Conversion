package com.rmsConversion.RMSnew.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.AndroidDashboardMaster;

import java.util.List;

@Repository
public interface DashBoardMasterRepository extends JpaRepository<AndroidDashboardMaster, Long> {

    @Query(value="SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, android_dashboard_master.user_id FROM public.androidmenumst inner join android_dashboard_master on androidmenumst.mid=android_dashboard_master.mid and android_dashboard_master.user_id=?1 where androidmenumst.role='ROLE_USER' order by mid asc",nativeQuery=true)
    public List<Object[]> androidgetAllocatedMenuByUserId(Long userId);

    @Query(value="SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, android_dashboard_master.manager_id FROM public.androidmenumst inner join android_dashboard_master on androidmenumst.mid=android_dashboard_master.mid and android_dashboard_master.manager_id=?1 where androidmenumst.role='ROLE_MANAGER' order by mid asc",nativeQuery=true)
    public List<Object[]> androidgetAllocatedMenuByManagerId(Long managerId);

} 