package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.DashBoardLayout;


@Repository
public interface DashboardAPIRepository extends JpaRepository<DashBoardLayout, Long> {
	

    @Query(value = "SELECT TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS'), json_data.value AS digitalvalue, 'Analog' as data_type "
			+ "FROM history h, jsonb_each_text(h.devicedata->'Analog') AS json_data " + "WHERE json_data.key=:prmname "
			+ "AND cast(h.device_date as date) = '2025-07-31' " + "AND h.device_id=:deviceId " + "UNION ALL "
			+ "SELECT TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS'), json_data.value AS digitalvalue, 'Digital' as data_type "
			+ "FROM history h, jsonb_each_text(h.devicedata->'Digital') AS json_data " + "WHERE json_data.key=:prmname "
			+ "AND cast(h.device_date as date) = '2025-07-31' " + "AND h.device_id=:deviceId " + "ORDER BY 1 DESC "
			+ "LIMIT CASE WHEN :limit > 0 THEN :limit END", nativeQuery = true)
	List<Object[]> GetDeviceParameterRecords(@Param("deviceId") Long deviceId, @Param("prmname") String prmname,
			@Param("limit") int limit);

	@Query(value = "SELECT TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS'), json_data.value AS digitalvalue, 'Analog' as data_type "
			+ "FROM history h, jsonb_each_text(h.devicedata->'Analog') AS json_data " + "WHERE json_data.key=:prmname "
			+ "AND cast(h.device_date as date) = '2025-07-31' " + "AND h.device_id=:deviceId " + "UNION ALL "
			+ "SELECT TO_CHAR(h.device_date, 'YYYY-MM-DD HH24:MI:SS'), json_data.value AS digitalvalue, 'Digital' as data_type "
			+ "FROM history h, jsonb_each_text(h.devicedata->'Digital') AS json_data " + "WHERE json_data.key=:prmname "
			+ "AND cast(h.device_date as date) = '2025-07-31' " + "AND h.device_id=:deviceId "
			+ "ORDER BY 1 DESC", nativeQuery = true)
	List<Object[]> GetAllDeviceParameterRecords(@Param("deviceId") Long deviceId, @Param("prmname") String prmname);

}
