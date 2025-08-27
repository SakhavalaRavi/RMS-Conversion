package com.rmsConversion.RMSnew.Service;

import java.util.List;

import com.rmsConversion.RMSnew.Model.Analogdata;

public interface AnalogDataServices {

    List<Analogdata> getlist();

    Analogdata get(int id);

    String update(Analogdata bs);

    List<Object[]> getDigitaldatalist();

    List<Object[]> getAnalogdatalist();

    void save(Analogdata bs);

    String delete(int id);
  
}
