package com.rmsConversion.RMSnew.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.Componet;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Repository.ComponetRepository;

@Service("Componentservice")
public class ComponentServicesImpl implements ComponentServices {

    @Autowired
	ComponetRepository repository;

    public void save(Componet bs) {
		repository.save(bs);
		
	}

    public List<Componet> getlist() {
		return repository.findAll();
	}

    public Componet get(Long id) {
		return repository.findById(id).orElse(null);
	}

    public String update(Componet bs) {
        repository.saveAndFlush(bs);
       return new SpringException(true, "Componet sucessfully Updated").toString();
   }

   public String delete(Long id) {
    repository.deleteById(id);
    return new SpringException(true, "Componet sucessfully Deleted").toString();
}

}
