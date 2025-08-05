package com.rmsConversion.RMSnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.AndroidAllocationMst;
import com.rmsConversion.RMSnew.Model.AndroidDashboardMaster;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Model.User;
import com.rmsConversion.RMSnew.Model.UserRole;
import com.rmsConversion.RMSnew.Service.AndroidDashboardService;
import com.rmsConversion.RMSnew.Service.AndroidMenuService;
import com.rmsConversion.RMSnew.Service.UserService;
import com.rmsConversion.RMSnew.Repository.AndroidMenuAllocationRepository;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AndroidMenuController {
    
    @Autowired
    private AndroidMenuService androidMenuService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AndroidDashboardService androidDashboardService;
    
    @Autowired
    private AndroidMenuAllocationRepository menuAlloRepo;
    
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
    
    @RequestMapping(value = "/androidallocateRedirectUrl", produces = { "application/json" }, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> allocateRedirectUrl(@RequestParam(value = "uid") Long uid,
                                                                   @RequestParam(value = "mid") Long mid) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.getuserbyid(uid);
            for (UserRole role : user.getUserRole()) {
                String r = role.getRole().toString();

                AndroidDashboardMaster existing = null;

                if ("ROLE_USER".equalsIgnoreCase(r)) {
                    existing = androidDashboardService.findByUserIdAndRole(uid, r);
                } else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
                    existing = androidDashboardService.findByManagerIdAndRole(uid, r);
                } else {
                    continue;
                }

                if (existing != null) {
                    existing.setMid(mid);
                    androidDashboardService.save(existing);
                } else {
                    AndroidDashboardMaster adm = new AndroidDashboardMaster();
                    adm.setMid(mid);
                    adm.setRole(r);

                    if ("ROLE_USER".equalsIgnoreCase(r)) {
                        adm.setUserId(uid);
                        adm.setManagerId(0L);
                    } else if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
                        adm.setManagerId(uid);
                        adm.setUserId(0L);
                    }

                    androidDashboardService.save(adm);
                }
            }

            response.put("status", "success");
            response.put("message", "Redirect URL allocated successfully.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        }
    
    @RequestMapping(value = "/androidgetAllocatedMenuById/{id}", produces = {
            "application/json" }, method = RequestMethod.GET)
    public String androidgetMenuById(@PathVariable Long id) {
        User user = userService.getuserbyid(id);
        JSONArray jarray = new JSONArray();
        for (UserRole role : user.getUserRole()) {
            String r = role.getRole().toString();

            if ("ROLE_USER".equalsIgnoreCase(r)) {
                List<Object[]> list = androidMenuService.androidgetAllocatedMenuByUserId(id);
                if (!list.isEmpty()) {
                    for (Object[] result1 : list) {
                        Map<String, String> jsonOrderedMap = new LinkedHashMap<>();
                        jsonOrderedMap.put("id", result1[0].toString());
                        jsonOrderedMap.put("menuname", result1[1].toString());
                        jsonOrderedMap.put("url", result1[2].toString());
                        jsonOrderedMap.put("allocation", result1[3].toString());
                        jsonOrderedMap.put("userid", result1[4] == null ? "0" : result1[4].toString());
                        jarray.put(jsonOrderedMap);
                    }
                }
            }

            if ("ROLE_MANAGER".equalsIgnoreCase(r)) {
                List<Object[]> list = androidMenuService.androidgetAllocatedMenuByManagerId(id);
                if (!list.isEmpty()) {
                    for (Object[] result1 : list) {
                        Map<String, String> jsonOrderedMap = new LinkedHashMap<>();
                        jsonOrderedMap.put("id", result1[0].toString());
                        jsonOrderedMap.put("menuname", result1[1].toString());
                        jsonOrderedMap.put("url", result1[2].toString());
                        jsonOrderedMap.put("allocation", result1[3].toString());
                        jsonOrderedMap.put("managerid", result1[4] == null ? "0" : result1[4].toString());
                        jarray.put(jsonOrderedMap);
                    }
                }

            }
        }
        return jarray.toString();
    }
    
    @RequestMapping(value = "/androidAllocateMenuForBoth", produces = {
            "application/json" }, method = RequestMethod.POST)
    public String androidAllocateMenuForBoth(@RequestParam(value = "userid") Long userid,
            @RequestParam(value = "allocated[]") Integer[] allocated) {

        boolean isManager = true;

        try {
            List<AndroidAllocationMst> userRecords = menuAlloRepo.findByUserId(userid);

            if (!userRecords.isEmpty()) {
                androidMenuService.androiddeleteUserMenu(userid);
                isManager = false;

            } else {
                menuAlloRepo.androiddeleterMenu(userid);
                isManager = true;
            }

        } catch (Exception e) {
            try {
                List<AndroidAllocationMst> managerRecords = menuAlloRepo.findByManagerId(userid);
                if (!managerRecords.isEmpty()) {
                    menuAlloRepo.androiddeleterMenu(userid);
                    isManager = true;
                } else {
                    androidMenuService.androiddeleteUserMenu(userid);
                    isManager = false;
                }
            } catch (Exception ex) {
                androidMenuService.androiddeleteUserMenu(userid);
                isManager = false;
            }
        }

        for (int i = 0; i < allocated.length; i++) {
            if (allocated[i] != 0) {
                AndroidAllocationMst mst = new AndroidAllocationMst();

                if (isManager) {
                    mst.setManagerId(userid);
                    mst.setUserId(0L);
                    System.out.println("Saving menu " + allocated[i] + " with Manager ID: " + userid + ", User ID: 0");
                } else {
                    mst.setManagerId(0L);
                    mst.setUserId(userid);
                    System.out.println("Saving menu " + allocated[i] + " with User ID: " + userid + ", Manager ID: 0");
                }

                mst.setMid(Long.valueOf("" + allocated[i]));
                androidMenuService.androidnewMenu(mst);
            }
        }

        String message = isManager ? "Menu Successfully Allocated to Manager!" : "Menu Successfully Allocated to User!";
        return new SpringException(true, message).toString();
    }


    
}  