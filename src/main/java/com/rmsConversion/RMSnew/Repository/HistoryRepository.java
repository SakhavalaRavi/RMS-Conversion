package com.rmsConversion.RMSnew.Repository;

import java.sql.Timestamp;
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

	@Query(value = "SELECT CAST(devicedata AS text), device_date FROM history " + "WHERE device_id = :deviceId "
			+ "AND device_date BETWEEN :sdate AND :edate " + "ORDER BY device_date "
			+ "LIMIT :limit", nativeQuery = true)
	List<Object[]> findDeviceDataByDeviceIdAndDateRangeWithLimit(@Param("deviceId") Long deviceId,
			@Param("sdate") Timestamp sdate, @Param("edate") Timestamp edate, @Param("limit") int limit);

	@Query(value = "SELECT CAST(devicedata AS text), device_date FROM history " + "WHERE device_id = :deviceId "
			+ "AND device_date BETWEEN :sdate AND :edate " + "ORDER BY device_date", nativeQuery = true)
	List<Object[]> findDeviceDataByDeviceIdAndDateRange(@Param("deviceId") Long deviceId,
			@Param("sdate") Timestamp sdate, @Param("edate") Timestamp edate);

			@Query(value = "SELECT DISTINCT DATE_TRUNC('day', device_date) Created_date,     MIN(device_date) OVER (PARTITION BY DATE_TRUNC('day', device_date)) fst_record,     FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('day', device_date)                               ORDER BY device_date ASC) fst_Value ,    MAX(device_date) OVER (PARTITION BY DATE_TRUNC('day', device_date)) last_record,     FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('day', device_date)      ORDER BY device_date DESC) last_Value,                          FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('day', device_date)                               ORDER BY device_date DESC) -                                  FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('day', device_date)                               ORDER BY device_date ASC) diff_in_Value FROM history, jsonb_each_text(devicedata->'Analog') AS json_data WHERE  json_data.key=:paramId and device_date  between TO_DATE(:startDate, 'YYYY-MM-DD') and TO_DATE(:endDate, 'YYYY-MM-DD')+ interval '1 day' and device_id=:deviceId  order by Created_date ", nativeQuery = true)
	List<Object[]> gerMonthlyBarChartData(@Param("deviceId") long paramLong, @Param("paramId") String paramString1,
			@Param("startDate") String paramString2, @Param("endDate") String paramString3);

	@Query(value = "SELECT DISTINCT DATE_TRUNC('month', device_date) Created_date,     MIN(device_date) OVER (PARTITION BY DATE_TRUNC('month', device_date)) fst_record,     FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('month', device_date)                               ORDER BY device_date ASC) fst_Value ,    MAX(device_date) OVER (PARTITION BY DATE_TRUNC('month', device_date)) last_record,     FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('month', device_date)      ORDER BY device_date DESC) last_Value,                          FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('month', device_date)                               ORDER BY device_date DESC) -                                  FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('month', device_date)                               ORDER BY device_date ASC) diff_in_Value FROM history, jsonb_each_text(devicedata->'Analog') AS json_data WHERE  json_data.key=:paramId and device_date  between TO_DATE(:startDate, 'YYYY-MM-DD') and TO_DATE(:endDate, 'YYYY-MM-DD')+ interval '1 day' and device_id=:deviceId  order by Created_date ", nativeQuery = true)
	List<Object[]> getYearlyBarChartData(@Param("deviceId") long paramLong, @Param("paramId") String paramString1,
			@Param("startDate") String paramString2, @Param("endDate") String paramString3);

	@Query(value = "SELECT DISTINCT DATE_TRUNC('hour', device_date) Created_date,     MIN(device_date) OVER (PARTITION BY DATE_TRUNC('hour', device_date)) fst_record,\r\n    FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('hour', device_date)                               ORDER BY device_date ASC) fst_Value ,     MAX(device_date) OVER (PARTITION BY DATE_TRUNC('hour', device_date)) last_record,\r\n    FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('hour', device_date)      ORDER BY device_date DESC) last_Value,                                                 FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('hour', device_date)                               ORDER BY device_date DESC) -                                        FIRST_VALUE(cast(json_data.value as double precision))  OVER (PARTITION BY DATE_TRUNC('hour', device_date)                               ORDER BY device_date ASC) diff_in_Value FROM history, jsonb_each_text(devicedata->'Analog') AS json_data WHERE  json_data.key=:paramId and device_date  between TO_DATE(:startDate, 'YYYY-MM-DD') and TO_DATE(:endDate, 'YYYY-MM-DD')+ interval '1 day' and device_id=:deviceId  order by Created_date ", nativeQuery = true)
	List<Object[]> getDailyBarChartData(@Param("deviceId") long paramLong, @Param("paramId") String paramString1,
			@Param("startDate") String paramString2, @Param("endDate") String paramString3);

			@Query(value = "select analogs ->>'Analogunit' analogunit  from public.deviceprofile,jsonb_array_elements(parameters->'Analog') analogs where analogs->>'Analoginput'=:analoginput AND prid=:prid", nativeQuery = true)
	List<Object[]> getprofileanalogunit(@Param("prid") Long paramLong, @Param("analoginput") String paramString);

	@Query(value = "select hs.no,TO_CHAR(device_date, 'YYYY-MM-DD HH24:MI:SS') as device_date,hs.gpsdata->>'latitude' as latitude,hs.gpsdata->>'longitude' as longitude,hs.gpsdata->>'speed' as speed,hs.gpsdata->>'angle' as angle,hs.gpsdata->>'odometer'   as odometer,hs.gpsdata->>'dig_input_2' as digitalinput from history hs where hs.device_id=:deviceid  and hs.device_date  between TO_DATE(:startdate, 'YYYY-MM-DD HH24:MI:SS') and TO_DATE(:enddate,'YYYY-MM-DD HH24:MI:SS')+ interval '1 day' and (hs.gpsdata->>'latitude' IS NOT NULL or hs.gpsdata->>'longitude' IS NOT NULL ) order by device_date asc limit :max", nativeQuery = true)
	List<Object[]> getdeviceHistoryLocation(@Param("deviceid") Long paramLong1, @Param("startdate") String paramString1,
			@Param("enddate") String paramString2, @Param("max") Long paramLong2);


			@Query(value = "select hs.no,TO_CHAR(device_date, 'YYYY-MM-DD HH24:MI:SS') as device_date,hs.gpsdata->>'latitude' as latitude,hs.gpsdata->>'longitude' as longitude,hs.gpsdata->>'speed' as speed,hs.gpsdata->>'angle' as angle ,dm.devicename as devicename from history hs  join devicemaster dm on dm.deviceid=hs.device_id where hs.device_id=:deviceid  and hs.device_date  between TO_DATE(:startdate, 'YYYY-MM-DD HH24:MI:SS') and TO_DATE(:enddate,'YYYY-MM-DD HH24:MI:SS')+ interval '1 day' and (hs.gpsdata->>'latitude' IS NOT NULL or hs.gpsdata->>'longitude' IS NOT NULL) order by device_date asc  limit :max", nativeQuery = true)
	List<Object[]> getadminHistoryLocation(@Param("deviceid") Long paramLong1, @Param("startdate") String paramString1,
			@Param("enddate") String paramString2, @Param("max") Long paramLong2);

			@Query(value = "SELECT h.device_id AS deviceId, " + "h.gpsdata->>'latitude' AS latitude, "
			+ "h.gpsdata->>'longitude' AS longitude, " + "h.gpsdata->>'speed' AS speed, "
			+ "h.gpsdata->>'angle' AS angle, " + "TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS') AS deviceDate "
			+ "FROM history h " + "WHERE h.device_id = :deviceId "
			+ "AND h.device_date BETWEEN CAST(:startDate AS timestamp) AND CAST(:endDate AS timestamp) "
			+ "ORDER BY h.device_date ASC", nativeQuery = true)
	List<Object[]> getAllSpeedData(@Param("deviceId") int deviceId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	@Query(value = "SELECT h.device_id AS deviceId, " + "h.gpsdata->>'latitude' AS latitude, "
			+ "h.gpsdata->>'longitude' AS longitude, " + "h.gpsdata->>'speed' AS speed, "
			+ "h.gpsdata->>'angle' AS angle, " + "TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS') AS deviceDate "
			+ "FROM history h " + "WHERE h.device_id = :deviceId "
			+ "AND h.device_date BETWEEN CAST(:startDate AS timestamp) AND CAST(:endDate AS timestamp) "
			+ "AND CAST(h.gpsdata->>'speed' AS FLOAT) > 0 " + "ORDER BY h.device_date ASC", nativeQuery = true)
	List<Object[]> getNonZeroSpeedData(@Param("deviceId") int deviceId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

			@Query(value = "SELECT l.device_id AS deviceId, " + "l.gpsdata->>'latitude' AS latitude, "
			+ "l.gpsdata->>'longitude' AS longitude, " + "l.gpsdata->>'speed' AS speed, "
			+ "l.gpsdata->>'angle' AS angle, " + "TO_CHAR(l.device_date, 'YYYY-MM-DD HH24:MI:SS') AS deviceDate "
			+ "FROM history l " + "WHERE l.device_id = :deviceId "
			+ "AND l.device_date BETWEEN CAST(:startDate AS timestamp) AND CAST(:endDate AS timestamp) "
			+ "ORDER BY l.device_date ASC", nativeQuery = true)
	List<Object[]> getHistoryData(@Param("deviceId") int deviceId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

			@Query(value = "SELECT device_id, " + "TO_CHAR(device_date, 'YYYY-MM-DD HH24:MI:SS') AS device_date, "
			+ "gpsdata->>'latitude' AS latitude, " + "gpsdata->>'longitude' AS longitude, "
			+ "gpsdata->>'angle' AS angle, " + "gpsdata->>'speed' AS speed " + "FROM lasttrack "
			+ "WHERE device_id = :deviceId " + "ORDER BY device_date DESC", nativeQuery = true)
	List<Object[]> findAllByDeviceIdAndDateRange(@Param("deviceId") Long deviceId);
}
