package com.rmsConversion.RMSnew.Controller;

import com.rmsConversion.RMSnew.Model.MenuAllocationMst;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.MenuAllocationService;
import com.rmsConversion.RMSnew.Service.MenuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MenuController {
    
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private MenuAllocationService alloService;
    
    @GetMapping(value = "/getmenu/{managerId}", produces = {"application/json"})
    public String getmenu(@PathVariable Long managerId) {
        List<Object[]> list = menuService.getMenu(managerId);
        
        JSONArray jarray = new JSONArray();
        System.out.println("list : " + list.size());
        if (!list.isEmpty()) {
            for (Object[] result1 : list) {
                JSONObject jo = new JSONObject();
                jo.put("id", result1[0].toString());
                jo.put("menuname", result1[1].toString());
                jo.put("url", result1[2].toString());
                jo.put("allocation", result1[3].toString());
                jo.put("userid", result1[4] == null ? 0 : result1[4].toString());
                jarray.put(jo);
            }
        }
        return jarray.toString();
    }
    
    @RequestMapping(value = "/allocatemenu", produces = { "application/json" }, method = RequestMethod.POST)
    public String allocatemenu(@RequestParam(value="managerId") Long managerId,
            @RequestParam(value="allocated[]") Integer[] allocated) {
        
        System.out.println("managerId: " + managerId);
        if (allocated != null && allocated.length > 0) {
            System.out.println("allocated[0]: " + allocated[0]);
        }

        menuService.deleteMenu(managerId);
        
        if (allocated != null) {
            for(int i=0; i<allocated.length; i++) {
                if(allocated[i] != null && allocated[i] != 0) {
                    MenuAllocationMst mst = new MenuAllocationMst();
                    mst.setManagerId(managerId);
                    mst.setMid(Long.valueOf(""+allocated[i]));
                    mst.setUserId(0L);
                    alloService.newMenu(mst);
                }
            }
        }
        return new SpringException(true, "Menu Successfully Allocated!").toString();
    }
} 