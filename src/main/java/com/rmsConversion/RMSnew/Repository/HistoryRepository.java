package com.rmsConversion.RMSnew.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rmsConversion.RMSnew.Model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
}
