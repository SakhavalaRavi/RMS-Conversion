package com.rmsConversion.RMSnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.AndroidMenuService;
import com.rmsConversion.RMSnew.Service.UserService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AndroidMenuController {
    
    @Autowired
    private AndroidMenuService androidMenuService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/androidgetmenu/{userid}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<String> getAndroidMenu(@PathVariable Long userid) {
        JSONArray jarray = new JSONArray();
        
        try {
            User user = userService.getuserbyid(userid);
            
            if (user == null || user.getUserRole() == null || user.getUserRole().isEmpty()) {
                System.out.println("Error: User or roles are null for userId = " + userid);
                JSONObject error = new JSONObject();
                error.put("error", "User or roles not found.");
                return ResponseEntity.badRequest().body(new JSONArray().put(error).toString());
            }
            
            Set<UserRole> roles = user.getUserRole();
            List<Object[]> userMenuList;
            List<Object[]> managerMenuList;
            
            for (UserRole role : roles) {
                String roleName = role.getRole() != null ? role.getRole().toString() : "";
                
                if (roleName.equalsIgnoreCase("ROLE_USER")) {
                    userMenuList = androidMenuService.getMenuByUserId(userid);
                    System.out.println("ROLE_USER menu size: " + userMenuList.size());
                    
                    for (Object[] result1 : userMenuList) {
                        if (result1 == null) continue;
                        
                        JSONObject jo = new JSONObject();
                        jo.put("id", result1[0] != null ? result1[0].toString() : "");
                        jo.put("menuname", result1[1] != null ? result1[1].toString() : "");
                        jo.put("url", result1[2] != null ? result1[2].toString() : "");
                        jo.put("allocation", result1[3] != null ? result1[3].toString() : "");
                        jo.put("userid", result1[4] != null ? result1[4].toString() : "0");
                        
                        jarray.put(jo);
                    }
                    
                } else if (roleName.equalsIgnoreCase("ROLE_MANAGER")) {
                    managerMenuList = androidMenuService.getMenuByManagerId(userid);
                    System.out.println("ROLE_MANAGER menu size: " + managerMenuList.size());
                    
                    for (Object[] result1 : managerMenuList) {
                        if (result1 == null) continue;
                        
                        JSONObject jo = new JSONObject();
                        jo.put("id", result1[0] != null ? result1[0].toString() : "");
                        jo.put("menuname", result1[1] != null ? result1[1].toString() : "");
                        jo.put("url", result1[2] != null ? result1[2].toString() : "");
                        jo.put("allocation", result1[3] != null ? result1[3].toString() : "");
                        jo.put("userid", result1[4] != null ? result1[4].toString() : "0");
                        
                        jarray.put(jo);
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", "Exception occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new JSONArray().put(error).toString());
        }
        
        return ResponseEntity.ok(jarray.toString());
    }
    
    @RequestMapping(value = "/androidGetMenuById/{uid}", produces = { "application/json" }, method = RequestMethod.GET)
    public ResponseEntity<String> androidGetMenuById(@PathVariable Long uid) {
        try {
            User user = userService.getuserbyid(uid);
            Set<UserRole> roles = user.getUserRole();
            List<Object[]> managerMenuList = new ArrayList<>();
            List<Object[]> userMenuList = new ArrayList<>();

            JSONArray jarray = new JSONArray();
            for (UserRole role : roles) {
                if (role.getRole().toString().equalsIgnoreCase("ROLE_USER")) {
                    userMenuList = androidMenuService.getMenuByUserId(uid);
                    System.out.println("size" + userMenuList.size());
                    if (!userMenuList.isEmpty()) {
                        for (Object[] result1 : userMenuList) {
                            JSONObject jo = new JSONObject();
                            jo.put("id", result1[0].toString());
                            jo.put("menuname", result1[1].toString());
                            jo.put("url", result1[2].toString());
                            jo.put("allocation", result1[3].toString());
                            jo.put("userid", result1[4] == null ? 0 : result1[4].toString());
                            jarray.put(jo);
                        }
                    }
                } else if (role.getRole().toString().equalsIgnoreCase("ROLE_MANAGER")) {	
                    managerMenuList = androidMenuService.getMenuByManagerId(uid);
                    if (!managerMenuList.isEmpty()) {
                        for (Object[] result1 : managerMenuList) {
                            JSONObject jo = new JSONObject();
                            jo.put("id", result1[0].toString());
                            jo.put("menuname", result1[1].toString());
                            jo.put("url", result1[2].toString());
                            jo.put("allocation", result1[3].toString());
                            jo.put("userid", result1[4] == null ? 0 : result1[4].toString());
                            jarray.put(jo);
                        }
                    }
                }
            }
            return ResponseEntity.ok(jarray.toString());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject error = new JSONObject();
            error.put("error", "Exception occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new JSONArray().put(error).toString());
        }
    }
    
    @RequestMapping(value = "/androidallocatemenu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<String> androidallocatemenu(@RequestParam(value="managerid") Long managerid,
            @RequestParam(value="allocated[]") Integer[] allocated) {
        
        try {
            System.out.println(managerid);
            System.out.println(allocated[0]);

            androidMenuService.androiddeleteMenu(managerid);
            
            for(int i=0; i<allocated.length; i++) {
                if(allocated[i] != 0) {
                    AndroidAllocationMst mst = new AndroidAllocationMst();
                    mst.setManagerId(managerid);
                    mst.setMid(Long.valueOf(""+allocated[i]));
                    mst.setUserId(0L);
                    androidMenuService.androidnewMenu(mst);
                }
            }
            
            return ResponseEntity.ok(new SpringException(true, "Menu Successfully Allocated!").toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new SpringException(false, "Error: " + e.getMessage()).toString());
        }
    }
    
    @RequestMapping(value = "/androidallocateUsermenu", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<String> androidallocateUsermenu(@RequestParam(value="userid") Long userid,
            @RequestParam(value="allocated[]") Integer[] allocated) {
        
        try {
            System.out.println(userid);
            System.out.println(allocated[0]);

            androidMenuService.androiddeleteUserMenu(userid);
            
            for(int i=0; i<allocated.length; i++) {
                if(allocated[i] != 0) {
                    AndroidAllocationMst mst = new AndroidAllocationMst();
                    mst.setManagerId(0L);
                    mst.setMid(Long.valueOf(""+allocated[i]));
                    mst.setUserId(userid);
                    androidMenuService.androidnewMenu(mst);
                }
            }
            
            return ResponseEntity.ok(new SpringException(true, "Menu Successfully Allocated!").toString());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new SpringException(false, "Error: " + e.getMessage()).toString());
        }
    }
} 