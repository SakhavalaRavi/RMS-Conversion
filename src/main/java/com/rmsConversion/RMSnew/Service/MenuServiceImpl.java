package com.rmsConversion.RMSnew.Service;

import com.rmsConversion.RMSnew.Repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuRepository repository;
    
    @Override
    public List<Object[]> getMenu(Long managerId) {
        return repository.GetAllMenu(managerId);
    }
    
    @Override
    public void deleteMenu(long userId) {
        repository.deleterMenu(userId);
    }
} 