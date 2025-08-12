package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.DeviceProfile;

@Repository
public interface DeviceProfileRepository extends JpaRepository<DeviceProfile, Long> {
    
    DeviceProfile findByPrid(Long prid);

    @Query(value="SELECT prid FROM deviceprofile where profilename = :profileName", nativeQuery=true)
    Long getProfileIdByProfileName(@Param("profileName") String profileName);

    @Query(value = " SELECT  " + " analog_elem ->> 'Analoginput' AS analogInput, "
			+ "analog_elem ->> 'analogname' AS analogName " + "FROM devicemaster dm  "
			+ "JOIN deviceprofile dp ON dm.prid_fk = dp.prid, "
			+ "jsonb_array_elements(dp.parameters -> 'Analog') AS analog_elem "
			+ "WHERE dm.deviceid = :deviceId", nativeQuery = true)
	List<Object[]> findAnalogParamsByDeviceId(@Param("deviceId") Long deviceId);

    @Query(value = "SELECT " + 
            "analog_elem ->> 'Analogunit' AS analogUnit, " +
            "analog_elem ->> 'analogname' AS analogName, " +
            "analog_elem ->> 'max' AS max, " +  
            "analog_elem ->> 'min' AS min " +   
            "FROM devicemaster dm " +
            "JOIN deviceprofile dp ON dm.prid_fk = dp.prid, " +
            "jsonb_array_elements(dp.parameters -> 'Analog') AS analog_elem " +
            "WHERE dm.deviceid = :deviceId", nativeQuery = true)
List<Object[]> findAnalogParamsByDeviceIdMinAndMax(@Param("deviceId") Long deviceId);

    @Query(value = "SELECT " + "analog_elem ->> 'Analogunit' AS analogUnit, "
			+ "analog_elem ->> 'analogname' AS analogName, " + "analog_elem ->> 'max' AS max, "
			+ "analog_elem ->> 'min' AS min " + "FROM devicemaster dm "
			+ "JOIN deviceprofile dp ON dm.prid_fk = dp.prid, "
			+ "jsonb_array_elements(dp.parameters -> 'Analog') AS analog_elem " + "WHERE dm.deviceid = :deviceId "
			+ "AND analog_elem ->> 'Analoginput' = :prmkey", nativeQuery = true)
	List<Object[]> findAnalogParamsByDeviceIdAndPrmkey(@Param("deviceId") Long deviceId,
			@Param("prmkey") String prmkey);
} 