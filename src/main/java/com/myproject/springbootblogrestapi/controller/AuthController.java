package com.myproject.springbootblogrestapi.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.springbootblogrestapi.dtos.JWTAuthResponse;
import com.myproject.springbootblogrestapi.dtos.LoginDto;
import com.myproject.springbootblogrestapi.dtos.SignUpDto;
import com.myproject.springbootblogrestapi.entity.Role;
import com.myproject.springbootblogrestapi.entity.User;
import com.myproject.springbootblogrestapi.repository.RoleRepository;
import com.myproject.springbootblogrestapi.repository.UserRepository;
import com.myproject.springbootblogrestapi.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Auth Controller for signup and signin")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder; 
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	@ApiOperation(value="REST API to signin user to blog app")
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> autenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
				                           loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		//if authenticated get username
		if(authenticate.isAuthenticated()) {
			LOG.info("The Authenticated user email or password is: {} ", loginDto.getUsernameOrEmail());
		}
		//get token from token provider
		
		String token = jwtTokenProvider.generateToken(authenticate);
		LOG.info("Token Granted:{}",token);
		//return ResponseEntity.ok(new JWTAuthResponse(token));
		return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	@ApiOperation(value="REST API to signup user to blog app")
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
		//Add a check if a username exists in a db
		if(userRepository.existsUserByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Username is already Taken!", HttpStatus.BAD_REQUEST);
		}
		
		//add check for amil exists in D
		if(userRepository.existsUserByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already Taken!", HttpStatus.BAD_REQUEST);
		}
		
		//create user Object
		User user = new User();
		user.setName(signUpDto.getName());
		user.setEmail(signUpDto.getEmail());
		user.setUsername(signUpDto.getUsername());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		//i want the user signing up to have admin priviledges
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		userRepository.save(user);
		return new ResponseEntity<>("User signed up Succesfully!", HttpStatus.OK);
		
	}
	
	
	
	
	
	
	

}
