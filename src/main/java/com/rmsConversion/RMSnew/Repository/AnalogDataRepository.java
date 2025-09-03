package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Analogdata;
import com.rmsConversion.RMSnew.Model.Devicemaster;

@Repository
public interface AnalogDataRepository extends JpaRepository<Analogdata, Integer> {

    @Query(value = "SELECT id, prmname, prmtype FROM parameter where prmtype='Digital'", nativeQuery = true)
	public List<Object[]> getDigitaldatalist();

    @Query(value = "SELECT id, prmname, prmtype FROM parameter where prmtype='Analog'", nativeQuery = true)
	public List<Object[]> getAnalogdatalist();

    Analogdata findBydevice(Devicemaster device);

}
