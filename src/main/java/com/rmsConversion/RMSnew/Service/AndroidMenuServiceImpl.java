package com.rmsConversion.RMSnew.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;
import com.rmsConversion.RMSnew.Model.AndroidMenuMst;
import com.rmsConversion.RMSnew.Repository.AndroidMenuAllocationRepository;
import com.rmsConversion.RMSnew.Repository.AndroidMenuRepository;
import com.rmsConversion.RMSnew.Repository.DashBoardMasterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AndroidMenuServiceImpl implements AndroidMenuService {
    
    @Autowired
    private AndroidMenuRepository androidMenuRepository;
    
    @Autowired
    private AndroidMenuAllocationRepository androidMenuAllocationRepository;
    
    @Autowired
    private DashBoardMasterRepository DRepository;
    
    @Override
    public List<Object[]> getMenuByUserId(Long userId) {
        return androidMenuRepository.getMenuByUserId(userId);
    }
    
    @Override
    public List<Object[]> getMenuByManagerId(Long managerId) {
        return androidMenuRepository.getMenuByManagerId(managerId);
    }
    
    @Override
    public void androiddeleteMenu(Long userId) {
        androidMenuAllocationRepository.androiddeleterMenu(userId);
    }
    
    @Override
    public void androidnewMenu(AndroidAllocationMst mst) {
        androidMenuAllocationRepository.save(mst);
    }
    
    @Override
    public List<Object[]> GetAllandroidMenu(Long userid) {
        return androidMenuRepository.GetAllandroidMenu(userid);
    }
    
    @Override
    public List<Object[]> GetAllandroidMenuForManager(Long managerid) {
        return androidMenuRepository.GetAllandroidMenuForManager(managerid);
    }
    
    @Override
    public void androiddeleteUserMenu(long userId) {
        androidMenuRepository.androiddeleteUserMenu(userId);
    }
    
    @Override
    public Optional<AndroidMenuMst> androidfindByMenuId(Long mid) {
        return androidMenuRepository.androidfindByMenuId(mid);
    }
    
    @Override
    public List<Object[]> androidgetAllocatedMenuByUserId(Long Id) {
        return DRepository.androidgetAllocatedMenuByUserId(Id);
    }
    
    @Override
    public List<Object[]> androidgetAllocatedMenuByManagerId(Long Id) {
        return DRepository.androidgetAllocatedMenuByManagerId(Id);
    }
} 