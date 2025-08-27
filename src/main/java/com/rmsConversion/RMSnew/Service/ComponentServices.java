package com.rmsConversion.RMSnew.Service;

import java.util.List;
import java.util.Map;

import com.rmsConversion.RMSnew.Model.Componet;

public interface ComponentServices {


    void save(Componet bs);

    List<Componet> getlist();

    Componet get(Long id);

    String delete(Long id);

    String update(Componet bs);
}
