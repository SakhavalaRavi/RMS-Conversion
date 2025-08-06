package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.DashboardMaster;

@Repository
public interface DashBoardMasterRepository extends JpaRepository<DashboardMaster, Long> {

	

@Query(value ="SELECT menuurl FROM menumst where mid=?1", nativeQuery = true)
public String getMenuUrl(long userId);


@Query(value="SELECT menumst.mid, menumst.menuname, menumst.menuurl, menumst.role, dashboardmaster.user_id FROM public.menumst inner join dashboardmaster on menumst.mid=dashboardmaster.mid and dashboardmaster.user_id=?1 where menumst.role='ROLE_USER' order by mid asc",nativeQuery=true)
public List<Object[]> getAllocatedMenuByUserId(long userId);

@Query(value="SELECT menumst.mid, menumst.menuname, menumst.menuurl, menumst.role, dashboardmaster.manager_id FROM public.menumst inner join dashboardmaster on menumst.mid=dashboardmaster.mid and dashboardmaster.manager_id=?1 where menumst.role='ROLE_MANAGER' order by mid asc",nativeQuery=true)
public List<Object[]> getAllocatedMenuByManagerId(long managerId);

@Query("SELECT d FROM DashboardMaster d WHERE d.userId = :userId AND d.role = :role")
public DashboardMaster findByUserIdAndRole(@Param("userId") Long userId, @Param("role") String role);

@Query("SELECT d FROM DashboardMaster d WHERE d.managerId = :managerId AND d.role = :role")
public DashboardMaster findByManagerIdAndRole(@Param("managerId") Long managerId, @Param("role") String role);

@Query(value ="SELECT menuurl FROM androidmenumst where mid=?1", nativeQuery = true)
public String androidgetMenuUrl(Long userId);

@Query(value="SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, dashboardmaster.user_id FROM public.androidmenumst inner join dashboardmaster on androidmenumst.mid=dashboardmaster.mid and dashboardmaster.user_id=?1 where androidmenumst.role='ROLE_USER' order by mid asc",nativeQuery=true)
public List<Object[]> androidgetAllocatedMenuByUserId(Long userId);

@Query(value="SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, dashboardmaster.manager_id FROM public.androidmenumst inner join dashboardmaster on androidmenumst.mid=dashboardmaster.mid and dashboardmaster.manager_id=?1 where androidmenumst.role='ROLE_MANAGER' order by mid asc",nativeQuery=true)
public List<Object[]> androidgetAllocatedMenuByManagerId(Long managerId);

@Query("SELECT d FROM DashboardMaster d WHERE " +
	       "(d.userId = :uid AND :role = 'ROLE_USER') OR " +
	       "(d.managerId = :uid AND :role = 'ROLE_MANAGER')")
DashboardMaster findByUserOrManagerAndRole(@Param("uid") Long uid, @Param("role") String role);

} 