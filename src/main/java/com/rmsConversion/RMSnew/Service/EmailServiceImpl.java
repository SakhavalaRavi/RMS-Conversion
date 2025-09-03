package com.rmsConversion.RMSnew.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmsConversion.RMSnew.Model.EmailTemplate;
import com.rmsConversion.RMSnew.Model.SpringException;
import com.rmsConversion.RMSnew.Repository.EmailTemplateRepository;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailTemplateRepository emailrepository;

	@Override
	public EmailTemplate saveemailtemplate(EmailTemplate emailtemplate) {
		return emailrepository.saveAndFlush(emailtemplate);
	}

	@Override
	public List<EmailTemplate> getemaillistByUser_id(Long userid) {
		return emailrepository.findByUser_id(userid);
	}

	@Override
	public void updateemailtemplate(EmailTemplate emailtemplate) {
		emailrepository.saveAndFlush(emailtemplate);
	}

	@Override
	public String deleteemailtemplate(Long eid) {
		emailrepository.deleteById(eid);
		return new SpringException(true, "Emailtemplate Sucessfully Deleted").toString();
	}
}
