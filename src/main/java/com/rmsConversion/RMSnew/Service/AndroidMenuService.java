package com.rmsConversion.RMSnew.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;
import com.rmsConversion.RMSnew.Model.AndroidMenuMst;
import com.rmsConversion.RMSnew.Repository.AndroidMenuAllocationRepository;
import com.rmsConversion.RMSnew.Repository.AndroidMenuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AndroidMenuService {
    
    @Autowired
    private AndroidMenuRepository androidMenuRepository;
    
    @Autowired
    private AndroidMenuAllocationRepository androidMenuAllocationRepository;
    
    public List<Object[]> getMenuByUserId(Long userId) {
        return androidMenuRepository.getMenuByUserId(userId);
    }
    
    public List<Object[]> getMenuByManagerId(Long managerId) {
        return androidMenuRepository.getMenuByManagerId(managerId);
    }
    
    public void androiddeleteMenu(Long userId) {
        androidMenuAllocationRepository.androiddeleterMenu(userId);
    }
    
    public void androidnewMenu(AndroidAllocationMst mst) {
        androidMenuAllocationRepository.save(mst);
    }
    
    public List<Object[]> GetAllandroidMenu(Long userid) {
        return androidMenuRepository.GetAllandroidMenu(userid);
    }
    
    public List<Object[]> GetAllandroidMenuForManager(Long managerid) {
        return androidMenuRepository.GetAllandroidMenuForManager(managerid);
    }
    
    public void androiddeleteUserMenu(long userId) {
        androidMenuRepository.androiddeleteUserMenu(userId);
    }
    
    public Optional<AndroidMenuMst> androidfindByMenuId(Long mid) {
        return androidMenuRepository.androidfindByMenuId(mid);
    }
} 