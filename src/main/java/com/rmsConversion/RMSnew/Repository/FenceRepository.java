package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rmsConversion.RMSnew.Model.FenceData;

@Repository
public interface FenceRepository extends JpaRepository<FenceData, Long> {

	@Query(value = "SELECT * FROM fencedata WHERE fenceid = :id AND managerid = :managerId", nativeQuery = true)
	FenceData findFenceByIdAndManagerId(@Param("id") Long id, @Param("managerId") Long managerId);

	@Query(value = "SELECT * FROM fencedata WHERE managerid = :managerId", nativeQuery = true)
	List<FenceData> findAllByManagerId(@Param("managerId") Long managerId);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM fencedata WHERE fenceid = :id AND managerid = :managerId", nativeQuery = true)
	int deleteFenceByFenceidAndManagerId(@Param("id") Long id, @Param("managerId") Long managerId);
} 