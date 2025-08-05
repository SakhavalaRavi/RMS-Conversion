package com.rmsConversion.RMSnew.Service;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;
import com.rmsConversion.RMSnew.Model.AndroidMenuMst;

import java.util.List;
import java.util.Optional;

public interface AndroidMenuService {
    
    List<Object[]> getMenuByUserId(Long userId);
    
    List<Object[]> getMenuByManagerId(Long managerId);
    
    void androiddeleteMenu(Long userId);
    
    void androidnewMenu(AndroidAllocationMst mst);
    
    List<Object[]> GetAllandroidMenu(Long userid);
    
    List<Object[]> GetAllandroidMenuForManager(Long managerid);
    
    void androiddeleteUserMenu(long userId);
    
    Optional<AndroidMenuMst> androidfindByMenuId(Long mid);
    
    List<Object[]> androidgetAllocatedMenuByUserId(Long Id);
    
    List<Object[]> androidgetAllocatedMenuByManagerId(Long Id);
} 