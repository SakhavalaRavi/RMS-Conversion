package com.rmsConversion.RMSnew.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rmsConversion.RMSnew.Model.AndroidMenuMst;

import java.util.List;
import java.util.Optional;

@Repository
public interface AndroidMenuRepository extends JpaRepository<AndroidMenuMst, Long> {
    
    @Query(value = "SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, androidallocationmst.user_id FROM public.androidmenumst LEFT JOIN androidallocationmst ON androidmenumst.mid = androidallocationmst.mid AND androidallocationmst.user_id = :userid WHERE androidmenumst.role = 'ROLE_USER' ORDER BY mid asc", nativeQuery = true)
    public List<Object[]> GetAllandroidMenu(@Param("userid") Long userid);

    @Query(value="SELECT androidmenumst.mid, androidmenumst.menuname, androidmenumst.menuurl, androidmenumst.role, androidallocationmst.manager_id FROM public.androidmenumst LEFT JOIN androidallocationmst ON androidmenumst.mid = androidallocationmst.mid AND androidallocationmst.user_id = :managerid WHERE androidmenumst.role = 'ROLE_MANAGER' ORDER BY mid asc", nativeQuery = true)
    public List<Object[]> GetAllandroidMenuForManager(@Param("managerid") Long managerid);
    
    @Query(value = "SELECT m.mid, m.menuname, m.menuurl, " +
            "CASE WHEN a.user_id IS NOT NULL THEN 'ROLE_USER' ELSE 'not_allocated' END as allocation, " +
            "a.user_id " +
            "FROM androidmenumst m " +
            "LEFT JOIN androidallocationmst a ON m.mid = a.mid AND a.user_id = :userId " +
            "WHERE m.role = 'ROLE_USER' " +
            "ORDER BY m.mid", nativeQuery = true)
    List<Object[]> getMenuByUserId(@Param("userId") Long userId);
    
    @Query(value = "SELECT m.mid, m.menuname, m.menuurl, " +
            "CASE WHEN a.manager_id IS NOT NULL THEN 'ROLE_MANAGER' ELSE 'not_allocated' END as allocation, " +
            "a.manager_id " +
            "FROM androidmenumst m " +
            "LEFT JOIN androidallocationmst a ON m.mid = a.mid AND a.manager_id = :managerId " +
            "WHERE m.role = 'ROLE_MANAGER' " +
            "ORDER BY m.mid", nativeQuery = true)
    List<Object[]> getMenuByManagerId(@Param("managerId") Long managerId);
    
    @Modifying
    @Transactional
    @Query(value = "delete from androidallocationmst where androidallocationmst.user_id=:userId", nativeQuery = true)
    public void androiddeleteUserMenu(@Param("userId") long userId);
    
    @Query(value = "SELECT * FROM androidmenumst WHERE mid = :mid", nativeQuery = true)
    Optional<AndroidMenuMst> androidfindByMenuId(@Param("mid") Long mid);
} 