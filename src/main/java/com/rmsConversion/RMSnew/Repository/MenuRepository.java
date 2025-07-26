package com.rmsConversion.RMSnew.Repository;

import com.rmsConversion.RMSnew.Model.MenuMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuMst, Long> {
    
    @Query(value = "SELECT menumst.mid, menumst.menuname, menumst.menuurl, menumst.role,menumst.header, menuallocationmst.manager_id FROM public.menumst left join menuallocationmst on menumst.mid=menuallocationmst.mid and menuallocationmst.manager_id=?1 where menumst.role='ROLE_MANAGER' order by mid asc ", nativeQuery = true)
	public List<Object[]> GetAllMenu(Long managerId);
	
	@Modifying
	@Transactional
	@Query(value = "delete from menuallocationmst where menuallocationmst.manager_id=?1", nativeQuery = true)
	public void deleterMenu(long managerId);
} 