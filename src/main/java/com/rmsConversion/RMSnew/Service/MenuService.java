package com.rmsConversion.RMSnew.Service;

import java.util.List;

import com.rmsConversion.RMSnew.Model.MenuMst;

public interface MenuService {
    List<Object[]> getMenu(Long managerId);
    void deleteMenu(long managerId);
    MenuMst findByMid(Long mid);
} 