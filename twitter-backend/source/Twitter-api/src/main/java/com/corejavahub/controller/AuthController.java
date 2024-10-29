package com.corejavahub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corejavahub.config.JwtTokenProvider;
import com.corejavahub.exception.UserException;
import com.corejavahub.model.User;
import com.corejavahub.model.Varification;
import com.corejavahub.repository.UserRepository;
import com.corejavahub.response.AuthResonse;
import com.corejavahub.service.CustomUserDetailsServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation;

	@PostMapping("/signup")
	public ResponseEntity<AuthResonse> createUserHandler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String password = user.getPassword();
		String fullName = user.getFullName();
		String birthDate = user.getBirthDate();

		// Check if the email already exists
		User isEmailExist = userRepository.findByEmail(email);
		if (isEmailExist != null) {
			throw new UserException("Email already exists");
		}

		// Create a new user and encode the password
		User createdUser = new User();
		createdUser.setEmail(email);
		createdUser.setPassword(passwordEncoder.encode(password));
		createdUser.setFullName(fullName);
		createdUser.setBirthDate(birthDate);
		createdUser.setVerification(new Varification());

		// Save the user to the database
		User savedUser = userRepository.save(createdUser);

		// Authenticate the user
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generate JWT token
		String token = jwtTokenProvider.generateToken(authentication);

		// Create response object
		AuthResonse authResonse = new AuthResonse(token, true);

		return new ResponseEntity<>(authResonse, HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResonse> signin(@RequestBody User user) {

		String username = user.getEmail();
		String password = user.getPassword();

		// Authenticate the user
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Generate JWT token
		String token = jwtTokenProvider.generateToken(authentication);

		// Create response object
		AuthResonse authResonse = new AuthResonse(token, true);

		return new ResponseEntity<>(authResonse, HttpStatus.ACCEPTED);
	}

	private Authentication authenticate(String username, String password) {

		// Load user details
		UserDetails userDetails = customUserDetailsServiceImplementation.loadUserByUsername(username);

		if (userDetails == null) {
			throw new BadCredentialsException("Invalid username.");
		}

		// Check if the provided password matches the stored hashed password
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password.");
		}

		// Return authenticated user with their authorities
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
