package com.rmsConversion.RMSnew.Service;

import com.rmsConversion.RMSnew.Model.User;
import java.util.Optional;
 
public interface UserService {
    Optional<User> findByUsername(String username);
} 