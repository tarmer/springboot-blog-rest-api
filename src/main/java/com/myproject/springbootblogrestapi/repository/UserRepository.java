package com.myproject.springbootblogrestapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.springbootblogrestapi.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	Optional<User>findByUsernameOrEmail(String username, String email);
	Optional<User>findByUsername(String username);
	Boolean existsUserByUsername(String username);
	Boolean existsUserByEmail(String email);

}
