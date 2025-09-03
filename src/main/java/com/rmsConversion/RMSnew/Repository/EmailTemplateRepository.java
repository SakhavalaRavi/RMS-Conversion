package com.rmsConversion.RMSnew.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.EmailTemplate;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    @Query("from EmailTemplate where userid=:userid")
	public List<EmailTemplate> findByUser_id(@Param("userid") Long userid);

	@Query("from EmailTemplate where templatetype=:templatetype and userid=:uid OR createdby='ROLE_ADMIN'")
	public List<EmailTemplate> findByTemplatetype(@Param("templatetype") String templatetype,@Param("uid") Long uid);
}
