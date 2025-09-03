package com.rmsConversion.RMSnew.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
	
	//  method to return Parameter directly instead of Optional
    public Parameter findByid(long id);
	
}
