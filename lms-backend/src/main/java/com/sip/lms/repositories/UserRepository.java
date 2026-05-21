package com.sip.lms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sip.lms.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

}
