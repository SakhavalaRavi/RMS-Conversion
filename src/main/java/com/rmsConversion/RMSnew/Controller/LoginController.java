package com.rmsConversion.RMSnew.Controller;

import com.rmsConversion.RMSnew.Model.DashboardMaster;
import com.rmsConversion.RMSnew.Model.MenuMst;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Service.DashBoardMasterService;
import com.rmsConversion.RMSnew.Service.MenuService;
import com.rmsConversion.RMSnew.Service.UserService;
import com.rmsConversion.RMSnew.springSecurity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import com.rmsConversion.RMSnew.Model.UserRole;
import java.util.Optional;

@CrossOrigin(origins = "*")
@Transactional
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    DashBoardMasterService dashboardService;
    @Autowired
    MenuService menuService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(value = "/user/UserLogin/{username}/{password}", method = RequestMethod.GET, produces = "application/json")
    public String loginUser(@PathVariable String username, @PathVariable String password) {
        JSONArray responseArray = new JSONArray();
        JSONObject response = new JSONObject();

        Optional<User> userOpt = userService.findByUsername(username)
                .filter(User::isEnabled)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(username);
            for (UserRole roleObj : user.getUserRole()) {
                String role = roleObj.getRole();
                if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
                    response.put("api", token);
                    response.put("responseCode", "SUCCESS");
                    response.put("uId", user.getId());
                    response.put("role", role);
                    response.put("username", user.getUsername());
                    responseArray.put(response);
                    return responseArray.toString();
                }
            }

            for (UserRole roleObj : user.getUserRole()) {
                String role = roleObj.getRole();
                DashboardMaster dashboard = null;
                String defaultURL = null;
                if ("ROLE_USER".equalsIgnoreCase(role)) {
                    dashboard = dashboardService.findByUserIdAndRole(user.getId(), role);
                } else if ("ROLE_MANAGER".equalsIgnoreCase(role)) {
                    dashboard = dashboardService.findByManagerIdAndRole(user.getId(), role);

                } else {
                    // log.info(" LOG BONRIX the role is:: " + user.getUserRole());
                    continue;
                }

                if (dashboard != null) {
                    MenuMst menu = menuService.findByMid(dashboard.getMid());
                    if (menu != null) {
                        // user.setRedirectUrl(menu.getMenuurl());
                        defaultURL = menu.getMenuurl();
                    }
                }

                response.put("api", token);
                response.put("responseCode", "SUCCESS");
                response.put("uId", user.getId());
                response.put("role", role);
                response.put("username", user.getUsername());
                response.put("redirectUrl", defaultURL);

                responseArray.put(response);
                return responseArray.toString();
            }
            response.put("responseCode", "FAIL");
            responseArray.put(response);
            return responseArray.toString();
        } else {
            response.put("responseCode", "FAIL");
            responseArray.put(response);
            return responseArray.toString();
        }
    }
}