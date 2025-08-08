package com.rmsConversion.RMSnew.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.rmsConversion.RMSnew.Model.History;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.HistoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class HistoryController {
    
    @Autowired
    private HistoryService historyService;
    
    @RequestMapping(method=RequestMethod.POST,value="/history", produces={"application/json"})
    public String saveHistory(@RequestBody History history)
    { 
        historyService.save(history);
        return new SpringException(true, "History Successfully Added").toString();
    }
    
    @RequestMapping(value="/history", produces={"application/json"})
    public List<History> getHistoryList()
    {
         return historyService.getlist();
    }
}
