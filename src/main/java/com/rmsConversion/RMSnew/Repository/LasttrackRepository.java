package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Lasttrack;

@Repository
public interface LasttrackRepository extends JpaRepository<Lasttrack, Long> {
	


	@Query(value = "SELECT pa.key, CAST(pa.value AS text), dm.devicename FROM public.lasttrack lt " +
			"JOIN devicemaster dm ON dm.deviceid = lt.device_id " +
			"JOIN jsonb_array_elements(lt.digitaldata->'Analog') d ON true " +
			"JOIN jsonb_each(d) pa ON true " +
			"WHERE lt.device_id = :deviceid", nativeQuery = true)
	List<Object[]> getdevicekeyvalbydid(@Param("deviceid") Long deviceid);

	@Query(value = "SELECT pa.key, CAST(pa.value AS text), dm.devicename FROM public.lasttrack lt " +
			"JOIN devicemaster dm ON dm.deviceid = lt.device_id " +
			"JOIN jsonb_array_elements(lt.digitaldata->'Digital') d ON true " +
			"JOIN jsonb_each(d) pa ON true " +
			"WHERE lt.device_id = :deviceid", nativeQuery = true)
	List<Object[]> getdevicekeyvaldigitalbydid(@Param("deviceid") Long deviceid);

    @Query(value = "SELECT TO_CHAR(device_date, 'DD-MM-YYYY HH24:MI:SS') FROM lasttrack WHERE device_id = :deviceId", nativeQuery = true)
	String findDeviceDateByDeviceId(@Param("deviceId") Long deviceId);
}
