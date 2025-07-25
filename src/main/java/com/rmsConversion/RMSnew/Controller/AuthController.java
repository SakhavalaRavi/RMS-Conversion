package com.rmsConversion.RMSnew.Controller;

import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Service.UserService;
import com.rmsConversion.RMSnew.springSecurity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/user/UserLogin/{username}/{password}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Map<String, Object>>> login(@PathVariable String username, @PathVariable String password) {
        return userService.findByUsername(username)
                .filter(User::isEnabled)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String token = jwtUtil.generateToken(username);
                    Map<String, Object> userInfo = Map.of(
                        "uId", user.getId(),
                        "role", "ROLE_USER",
                        "api", token,
                        "responseCode", "SUCCESS",
                        "username", user.getUsername()
                    );
                    return ResponseEntity.ok(List.of(userInfo));
                })
                .orElse(ResponseEntity.status(401).body(List.of(Map.of(
                    "responseCode", "FAILURE",
                    "error", "Invalid credentials"
                ))));
    }
} 