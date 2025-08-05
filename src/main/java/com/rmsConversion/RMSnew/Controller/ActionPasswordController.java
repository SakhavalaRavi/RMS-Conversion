package com.rmsConversion.RMSnew.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.ActionPassword;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.ActionPasswordService;
import com.rmsConversion.RMSnew.Service.UserService;
import com.rmsConversion.RMSnew.springSecurity.JwtUtil;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class ActionPasswordController {

	@Autowired
	private ActionPasswordService actionPasswordService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/insertPassword")
	public ResponseEntity<Map<String, Object>> insertPassword(
	        @RequestHeader("Authorization") String authorizationHeader,
	        @RequestBody ActionPassword actionPassword) {

	    Map<String, Object> response = new HashMap<>();
	    
	    String token = null;
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        token = authorizationHeader.substring(7);
	    }
	    
	    if (token == null || !jwtUtil.validateToken(token)) {
	        response.put("status", "error");
	        response.put("message", "Invalid or missing JWT token");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }
	    
	    Long managerId = actionPassword.getManagerId();
	    User user = userService.getuserbyid(managerId);
	    if (user == null) {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }

	    boolean authorized = false;
	    Set<UserRole> roles = user.getUserRole();
	    for (UserRole role : roles) {
	        String roleName = role.getRole().toString();
	        if (roleName.equalsIgnoreCase("ROLE_MANAGER")) {
	            authorized = true;
	            break;
	        }
	    }

	    if (!authorized) {
	        response.put("status", "error");
	        response.put("message", "You are not authorized");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	    }

	    response = actionPasswordService.insertActionPassword(actionPassword);
	    return ResponseEntity.ok(response);
	}

	@DeleteMapping("/deletePassword/{managerId}/{type}")
	public ResponseEntity<Map<String, Object>> deleteByManagerIdAndType(
	        @PathVariable Long managerId,
	        @PathVariable String type,
	        @RequestHeader("Authorization") String authorizationHeader) {

	    Map<String, Object> response = new HashMap<>();

	    String token = null;
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        token = authorizationHeader.substring(7);
	    }
	    
	    if (token == null || !jwtUtil.validateToken(token)) {
	        response.put("status", "error");
	        response.put("message", "Invalid or missing JWT token");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    User user = userService.getuserbyid(managerId);
	    if (user == null) {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }
	    
	    boolean authorized = false;
	    Set<UserRole> roles = user.getUserRole();
	    for (UserRole role : roles) {
	        String roleName = role.getRole().toString();
	        if (roleName.equalsIgnoreCase("ROLE_MANAGER")) {
	            authorized = true;
	            break;
	        }
	    }

	    if (!authorized) {
	        response.put("status", "error");
	        response.put("message", "You are not authorized");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	    }

	    response = actionPasswordService.deleteByManagerIdAndType(managerId, type);
	    return ResponseEntity.ok(response);
	}

	@PutMapping("/updatePassword/{managerId}/{type}/{oldPassword}/{newPassword}")
	public ResponseEntity<Map<String, Object>> updatePasswordByManagerIdAndType(
	        @PathVariable Long managerId,
	        @PathVariable String type,
	        @PathVariable String oldPassword,
	        @PathVariable String newPassword,
	        @RequestHeader("Authorization") String authorizationHeader) {

	    Map<String, Object> response = new HashMap<>();

	    String token = null;
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        token = authorizationHeader.substring(7);
	    }
	    
	    if (token == null || !jwtUtil.validateToken(token)) {
	        response.put("status", "error");
	        response.put("message", "Invalid or missing JWT token");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    User user = userService.getuserbyid(managerId);
	    if (user == null) {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }

	    boolean authorized = false;
	    Set<UserRole> roles = user.getUserRole();
	    for (UserRole role : roles) {
	        String roleName = role.getRole().toString();
	        if (roleName.equalsIgnoreCase("ROLE_MANAGER")) {
	            authorized = true;
	            break;
	        }
	    }

	    if (!authorized) {
	        response.put("status", "error");
	        response.put("message", "You are not authorized");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	    }

	    response = actionPasswordService.updatePasswordByManagerIdAndType(managerId, type, oldPassword, newPassword);
	    return ResponseEntity.ok(response);
	}

	@GetMapping("/getPasswordType/{managerId}")
	public ResponseEntity<Map<String, Object>> getPasswordType(
	        @PathVariable Long managerId,
	        @RequestHeader("Authorization") String authorizationHeader) {

	    Map<String, Object> response = new HashMap<>();

	    String token = null;
	    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	        token = authorizationHeader.substring(7);
	    }
	    
	    if (token == null || !jwtUtil.validateToken(token)) {
	        response.put("status", "error");
	        response.put("message", "Invalid or missing JWT token");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    User user = userService.getuserbyid(managerId);
	    if (user == null) {
	        response.put("status", "error");
	        response.put("message", "User not found");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	    }

	    boolean authorized = false;
	    Set<UserRole> roles = user.getUserRole();
	    for (UserRole role : roles) {
	        String roleName = role.getRole().toString();
	        if (roleName.equalsIgnoreCase("ROLE_MANAGER")) {
	            authorized = true;
	            break;
	        }
	    }

	    if (!authorized) {
	        response.put("status", "error");
	        response.put("message", "You are not authorized");
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
	    }

	    List<String> types = actionPasswordService.getTypesByManagerId(managerId);

	    response.put("status", "success");
	    response.put("type", types);

	    return ResponseEntity.ok(response);
	}
}


