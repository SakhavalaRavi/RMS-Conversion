package com.rmsConversion.RMSnew.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rmsConversion.RMSnew.Model.Componet;

@Repository
public interface ComponetRepository extends JpaRepository<Componet, Long> {

}
