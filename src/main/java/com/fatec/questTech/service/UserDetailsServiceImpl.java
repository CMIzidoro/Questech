package com.fatec.questTech.service;

import com.fatec.questTech.model.User;
import com.fatec.questTech.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) {
		User user = userRepository.findByUsername(usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail));
		return UserDetail.create(user);
	}
}
