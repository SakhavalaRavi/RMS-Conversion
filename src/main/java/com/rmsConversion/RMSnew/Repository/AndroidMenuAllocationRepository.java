package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;

public interface AndroidMenuAllocationRepository extends JpaRepository<AndroidAllocationMst, Long> {

	@Modifying
    @Transactional
    @Query(value = "delete from androidallocationmst where androidallocationmst.manager_id=:userId", nativeQuery = true)
    public void androiddeleterMenu(@Param("userId") long userId);
    
    public List<AndroidAllocationMst> findByManagerId(Long userid);

    public List<AndroidAllocationMst> findByUserId(Long userid);
} 