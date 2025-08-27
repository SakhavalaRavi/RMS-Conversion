package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Devicemaster;

@Repository
public interface DevicemasterRepository extends JpaRepository<Devicemaster, Long> {
    Devicemaster findBydeviceid(Long deviceId);

    @Query(value = "select dm.deviceid,dm.devicename,TO_CHAR(lt.device_date,'DD-MM-YYYY HH24:MI:SS') from devicemaster dm join lasttrack lt on lt.device_id=dm.deviceid join assignuserdevice ad on ad.device_id=dm.deviceid where dm.prid_fk=:prid and ad.user_id=:userid", nativeQuery = true)
    List<Object[]> getdevicebyprid(@Param("prid") long paramLong1, @Param("userid") long paramLong2);

    @Query(value = "SELECT dm.deviceid, dm.devicename " + "FROM devicemaster dm "
			+ "WHERE dm.prid_fk = :prid", nativeQuery = true)
	List<Object[]> getDeviceByProfileId(@Param("prid") long prid);

    @Query(value =
    "SELECT dm.deviceid, " +
    "       dm.devicename, " +
    "       dp.profilename, " +
    "       jsonb_array_elements_text(dp.parameters->'Digital') AS digital_value " +
    "FROM devicemaster dm " +
    "JOIN deviceprofile dp ON dm.prid_fk = dp.prid " +
    "WHERE dm.deviceid = :deviceId",
    nativeQuery = true
)
List<Object[]> getDeviceData(@Param("deviceId") Long deviceId);

@Query(value = "SELECT dm.deviceid, " + 
"       dm.devicename, " + 
"       dp.profilename, "
+ "       jsonb_array_elements_text(dp.parameters->'Digital') AS digital_value " + "FROM devicemaster dm "
+ "JOIN deviceprofile dp ON dm.prid_fk = dp.prid "
+ "WHERE dp.profilename IN (:profileList)", nativeQuery = true)
List<Object[]> getAllDeviceData(@Param("profileList") List<String> profileList);
}
