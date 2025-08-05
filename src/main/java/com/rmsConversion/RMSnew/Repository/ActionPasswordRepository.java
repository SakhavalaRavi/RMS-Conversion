package com.rmsConversion.RMSnew.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.ActionPassword;

@Repository
public interface ActionPasswordRepository extends JpaRepository<ActionPassword, Long> {

	Optional<ActionPassword> findByManagerIdAndTypeAndPassword(Long managerId, String type, String password);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO actionpassword (manager_id, type, password) VALUES (:managerId, :type, :password)", nativeQuery = true)
	int insertActionPassword(@Param("managerId") Long managerId, @Param("type") String type,
			@Param("password") String password);

	List<ActionPassword> findByManagerId(Long managerId);

	Optional<ActionPassword> findByManagerIdAndType(Long managerId, String type);

	@Query("SELECT DISTINCT ap.type FROM ActionPassword ap WHERE ap.managerId = :managerId")
	List<String> findDistinctTypesByManagerId(@Param("managerId") Long managerId);
} 