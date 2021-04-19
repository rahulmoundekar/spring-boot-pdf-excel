package com.app.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.entity.Role;
import com.app.entity.User;
import com.app.repo.RoleRepository;
import com.app.repo.UserRepository;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role role = roleRepository.findByRole("ADMIN");
		user.setRoles(new ArrayList<>(Arrays.asList(role)));
		return userRepository.save(user);
	}

	public User findUserByUserName(String userName) {
		return userRepository.findByUsername(userName);
	}

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
