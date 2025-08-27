package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

            @Query(value = "SELECT dp.profilename, dp.prid FROM deviceprofile dp " +
            "JOIN devicemaster dm ON dp.prid = dm.prid_fk " +
            "JOIN assignuserdevice ad ON ad.device_id = dm.deviceid " +
            "WHERE ad.user_id = :user_id " +
            "GROUP BY dp.prid, dp.profilename", nativeQuery = true)
public List<Object[]> assigndeviceprofilebyuidSimple(@Param("user_id") long user_id);

@Query(value = "SELECT dp.profilename, dp.prid FROM deviceprofile dp " +
            "JOIN devicemaster dm ON dp.prid = dm.prid_fk " +
            "JOIN assignuserdevice ad ON ad.device_id = dm.deviceid " +
            "WHERE ad.manager_id = :manager_id " +
            "GROUP BY dp.prid, dp.profilename", nativeQuery = true)
public List<Object[]> assigndeviceprofilebymanagerid1(@Param("manager_id") long manager_id);
	

    @Modifying   
	@Transactional
	@Query(value ="SELECT dp.profilename,dp.prid, COUNT(dm.deviceid) AS device_count FROM deviceprofile dp join devicemaster dm on dp.prid=dm.prid_fk join assignuserdevice ad on ad.device_id=dm.deviceid where ad.user_id=:user_id group by dp.prid ORDER BY device_count DESC", nativeQuery = true)
	public List<Object[]> Assigndeviceprofilebyuid(@Param("user_id") long user_id);
  
	@Query(value ="SELECT dp.profilename,dp.prid FROM deviceprofile dp join devicemaster dm on dp.prid=dm.prid_fk join assignuserdevice ad on ad.device_id=dm.deviceid where ad.manager_id=:manager_id group by dp.prid", nativeQuery = true)
	public List<Object[]> assigndeviceprofilebymanagerid(@Param("manager_id") long manager_id);

    @Query(value = "SELECT * FROM deviceprofile WHERE prid IN (:ids)", nativeQuery = true)
    List<DeviceProfile> getByPridList(@Param("ids") List<Long> ids);
} 