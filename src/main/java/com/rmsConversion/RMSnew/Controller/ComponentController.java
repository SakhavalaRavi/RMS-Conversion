package com.rmsConversion.RMSnew.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rmsConversion.RMSnew.Model.Componet;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Service.ComponentServices;

@RestController
@CrossOrigin(origins = { "*" })
@Transactional
@RequestMapping("/api")
public class ComponentController {

    @Autowired
	ComponentServices Componentservice;

    @RequestMapping(method=RequestMethod.POST,value="/componet")@ExceptionHandler(SpringException.class)
		public String savedata(@RequestBody Componet bs)
		{ 
			Componentservice.save(bs);
			return new SpringException(true, "Componet Sucessfully Added").toString();
		}

        @RequestMapping(value="/componet" ,produces={"application/json"})
		public List<Componet> getcomponetlist()
		{
			return Componentservice.getlist();
		}

        @RequestMapping(value="/componet/{id}",method=RequestMethod.GET)
        public Componet getcomponet(@PathVariable long id) {
            return Componentservice.get(id);
        }

            @RequestMapping(method=RequestMethod.PUT,value="/componet/{id}")
			public String updatecomponet(@RequestBody Componet bs,@PathVariable long id)
			{
				bs.setId(id);
				return Componentservice.update(bs);
			}

            @RequestMapping(method=RequestMethod.DELETE,value="/componet/{id}")
			public String deletecomponet(@PathVariable long id)
			{
				return Componentservice.delete(id);
			}

}
