package io.HCL.ppmtool.services;

import io.HCL.ppmtool.domain.User;
import io.HCL.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.HCL.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {

		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			// Username has to be unique (exception)
			// Make sure that password and confirmPassword match
			// We don't persist or show the confirmPassword
			newUser.setConfirmPassword("*****");
			return userRepository.save(newUser);
		} catch (Exception ex) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}

	}

}
