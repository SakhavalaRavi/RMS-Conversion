package com.rmsConversion.RMSnew.Repository;

import com.rmsConversion.RMSnew.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    @Query("from User where id=:userid")
	public User findByUserIdnew(@Param("userid") long userid);
	
} 