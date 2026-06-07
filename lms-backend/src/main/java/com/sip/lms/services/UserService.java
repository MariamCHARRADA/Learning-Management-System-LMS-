package com.sip.lms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.entities.User;

public interface UserService {
	
	public List<User>getAllUsers();
	public Optional<User> getUserById(long id);
	public void deleteUserById(long id);
	//public User addUser(MultipartFile file, String username, String firstname, String lastname, String email, String country, String password);
	public User updateUser(MultipartFile file, String username, String firstname, String lastname, String email, String country, String password, long id);

}
