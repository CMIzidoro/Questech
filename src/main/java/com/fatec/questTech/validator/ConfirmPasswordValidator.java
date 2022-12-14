package com.fatec.questTech.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fatec.questTech.model.User;
import com.fatec.questTech.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, String> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void initialize(ConfirmPassword arg0) {
	}

	@Override
	public boolean isValid(String pwd, ConstraintValidatorContext context) {
		User userLoged = getUserLoged();
		return passwordEncoder.matches(pwd, userLoged.getPassword());
	}

	public User getUserLoged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return userRepository.findByUsername(username).orElseThrow();
	}
}