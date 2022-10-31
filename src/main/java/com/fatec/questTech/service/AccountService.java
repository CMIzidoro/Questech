package com.fatec.questTech.service;

import com.fatec.questTech.dto.ChagePwdDto;
import com.fatec.questTech.model.User;
import com.fatec.questTech.model.UserRepository;
import com.fatec.questTech.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void dataSave(User user) {
		User userDB = repository.findByUuid(user.getUuid()).orElseThrow();
		userDB.setName(user.getName());
		userDB.setTel(user.getTel());
		repository.save(userDB);
	}

	public void pwdSave(ChagePwdDto pwdDto) {
		User userDB = repository.findByUuid(pwdDto.getUuid()).orElseThrow();
		userDB.setPassword(passwordEncoder.encode(pwdDto.getNewPassword()));
		repository.save(userDB);
	}
	
	public User getUserLoged() {
		return repository.findById(ServiceUtil.getIdUser()).orElseThrow();
	}

}
