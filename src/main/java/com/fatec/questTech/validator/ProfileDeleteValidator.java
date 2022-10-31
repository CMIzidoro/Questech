package com.fatec.questTech.validator;

import com.fatec.questTech.model.Profile;
import com.fatec.questTech.model.ProfileRepository;
import com.fatec.questTech.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfileDeleteValidator implements Validator {

	@Autowired
	ProfileRepository repository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		String uuid = (String) target;
		
		Profile p = repository.findByUuid(uuid).orElseThrow();

		if (null != p.getRoles() && !p.getRoles().isEmpty()) {
			errors.rejectValue("name", "profile.erro.delete");
		}else if(!userRepository.findByProfile(p).isEmpty()) {
			errors.rejectValue("name", "profile.erro.delete.user");
		}

	}

}
