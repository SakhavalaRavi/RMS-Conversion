package com.rmsConversion.RMSnew.Service;

import java.util.List;

public interface MenuService {
    List<Object[]> getMenu(Long managerId);
    void deleteMenu(long managerId);
} 