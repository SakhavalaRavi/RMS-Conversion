package com.rmsConversion.RMSnew.Repository;

import com.rmsConversion.RMSnew.Model.MenuAllocationMst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuAllocationRepository extends JpaRepository<MenuAllocationMst, Long> {
} 