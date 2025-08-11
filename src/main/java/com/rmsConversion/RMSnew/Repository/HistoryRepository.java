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
    @Query(value = "SELECT device_id,device_date, system_date FROM history " +
            "WHERE device_id = :deviceId AND device_date BETWEEN :startDate AND :endDate " +
            "ORDER BY device_date ASC", nativeQuery = true)
    List<Object[]> findDeviceDataInRange(@Param("deviceId") Long deviceId,
                                  @Param("startDate") Date startDate,
                                  @Param("endDate") Date endDate);
}
