package com.fatec.questTech.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import com.fatec.questTech.model.ProfileRepository;
import com.fatec.questTech.model.UserRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fatec.questTech.dto.RegisterDto;
import com.fatec.questTech.dto.SessionDto;
import com.fatec.questTech.model.User;
import com.fatec.questTech.model.ParametroRepository;
import com.fatec.questTech.util.EmailUtil;
import com.fatec.questTech.util.ServiceUtil;

@Service
@Transactional
public class LoginService {

	@Autowired
	UserRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProfileRepository profileRepository;
	
	@Autowired
	ParametroRepository prmRepository;

	@Autowired
	EmailUtil emailService;

	@Value("${system.url}")
	String systemUrll;

	public boolean checkEmail(String email) {
		return repository.existsByUsername(email);
	}

	public boolean checkUuid(String uuid) {
		return repository.existsByRecoverUuid(uuid);
	}

	public boolean checkUuidExpiration(String uuid) {
		User user = repository.findByRecoverUuid(uuid);
		return user.getRecoverExpiration().isBefore(LocalDateTime.now());
	}

	public void recoverEmail(String email) throws MessagingException, IOException {

		User user = repository.findByUsername(email).orElseThrow();
		String uuid = ServiceUtil.generateUuid();
		user.setRecoverUuid(uuid);
		user.setRecoverExpiration(LocalDateTime.now().plusHours(24));
		repository.save(user);

		Map<String, String> parameters = new HashMap<>();
		String link = systemUrll + "/recover-link?u=" + uuid;
		parameters.put("#link", link);
		parameters.put("#nome", user.getName());
		String template = "recover_pwd.html";

		String htmlTemplate = emailService.loadHtmlTemplate(template, parameters);
		emailService.htmlEmail(email, "recuperar email", htmlTemplate);

	}

	public void recoverSave(String uuid, String password) {
		User user = repository.findByRecoverUuid(uuid);
		user.setPassword(passwordEncoder.encode(password));
		user.setRecoverUuid(null);
		user.setRecoverExpiration(null);
		repository.save(user);
	}

	public void registerSave(RegisterDto dto) {
		User user = new User();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setName(dto.getNome());
		user.setUsername(dto.getEmail());
		user.setTel(dto.getTel());
		user.setUuid(ServiceUtil.generateUuid());
		user.setProfiles(profileRepository.findAll());
		user.setIsAccountNonLocked(true);
		userRepository.save(user);
	}

	public SessionDto getSessionDto() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		User user = repository.findByUsername(username).orElseThrow();

		SessionDto dto = new SessionDto();
		dto.setNomeUser(user.getName());
		dto.setIdUser(user.getId());
		dto.setParametros(prmRepository.findAll());

		return dto;
	}

}
