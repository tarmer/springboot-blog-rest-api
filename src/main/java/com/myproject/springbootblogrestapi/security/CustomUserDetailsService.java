package com.myproject.springbootblogrestapi.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myproject.springbootblogrestapi.entity.Role;
import com.myproject.springbootblogrestapi.entity.User;
import com.myproject.springbootblogrestapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	
	private UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


    //create a user object of type security from our entity type user
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user =userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).
				orElseThrow(()->new UsernameNotFoundException("User Not Found wih username or email:" + usernameOrEmail));
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	 //private method to change a set of roles to a set of granted authorities
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	

}
