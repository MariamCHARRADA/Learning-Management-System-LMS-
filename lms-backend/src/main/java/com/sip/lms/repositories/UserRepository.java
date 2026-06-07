package com.sip.lms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sip.lms.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{ //id type is Long
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	Optional<User> findByEmail(String email);

}
