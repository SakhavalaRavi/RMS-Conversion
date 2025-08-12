package com.rmsConversion.RMSnew.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rmsConversion.RMSnew.Model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(value = "SELECT * FROM history WHERE device_id = :deviceId AND device_date BETWEEN :start AND :end ORDER BY device_date ASC", nativeQuery = true)
	List<History> findByDeviceIdAndDateRange(@Param("deviceId") Long deviceId, @Param("start") Date start,
			@Param("end") Date end);

	@Query(value = "SELECT * FROM history WHERE no = ("
			+ "SELECT no FROM history WHERE device_id = :deviceId AND device_date < :date ORDER BY device_date DESC LIMIT 1) "
			+ "UNION ALL " + "SELECT * FROM history WHERE device_id = :deviceId AND device_date = :date " + "UNION ALL "
			+ "SELECT * FROM history WHERE no = ("
			+ "SELECT no FROM history WHERE device_id = :deviceId AND device_date > :date ORDER BY device_date ASC LIMIT 1)", nativeQuery = true)
	List<History> findSurroundingLatLong(@Param("deviceId") Long deviceId, @Param("date") Date date);

	@Query(value = "SELECT device_id, device_date, system_date FROM history " +
             "WHERE device_id = :deviceId AND device_date BETWEEN :startDate AND :endDate " +
             "ORDER BY device_date ASC", nativeQuery = true)
	List<Object[]> findDeviceDataInRange(@Param("deviceId") Long deviceId,
                                   @Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate);

	@Query(value = "SELECT CAST(devicedata AS text) FROM lasttrack WHERE device_id = :deviceId ORDER BY device_date DESC LIMIT 1", nativeQuery = true)
	String findLatestDeviceDataByDeviceId(@Param("deviceId") Long deviceId);
}
