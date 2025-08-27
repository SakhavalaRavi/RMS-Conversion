package com.rmsConversion.RMSnew.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.Analogdata;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Repository.AnalogDataRepository;

@Service
public class AnalogDataServicesImpl implements AnalogDataServices {

    @Autowired
	AnalogDataRepository repository;


    @Override
	public List<Analogdata> getlist() {
		return repository.findAll();
	}

	@Override
	public Analogdata get(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public String update(Analogdata bs) {
		repository.saveAndFlush(bs);
		return new SpringException(true, "AnalogData sucessfully Updated").toString();
	}

	@Override
	public List<Object[]> getDigitaldatalist() {
		return repository.getDigitaldatalist();
	}

	@Override
	public List<Object[]> getAnalogdatalist() {
		return repository.getAnalogdatalist();
	}

	@Override
	public void save(Analogdata bs) {
		repository.save(bs);

	}

	@Override
	public String delete(int id) {
		repository.deleteById(id);
		return new SpringException(true, "AnalogData sucessfully Deleted").toString();
	}
}
