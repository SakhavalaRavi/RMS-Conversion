package com.rmsConversion.RMSnew.Service;

import com.rmsConversion.RMSnew.Model.MenuAllocationMst;
import com.rmsConversion.RMSnew.Repository.MenuAllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuallocationmService")
public class MenuAllocationServiceImpl implements MenuAllocationService {

    @Autowired
    private MenuAllocationRepository repository;
    
    @Override
    public void newMenu(MenuAllocationMst mst) {
        repository.save(mst);
    }
} 