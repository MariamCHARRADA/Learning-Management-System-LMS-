package com.sip.lms.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.entities.User;
import com.sip.lms.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	

	@Override
	public List<User> getAllUsers() {
		return (List<User>)this.userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(long id) {
		return this.userRepository.findById(id);
	}
	
	/*@Override 
	public User addUser(MultipartFile file, String username, String firstname, String lastname, String email, String country, String password) {
		String newImageName = getSaltString().concat(file.getOriginalFilename());
		
		try {
			Files.copy(file.getInputStream(), this.root.resolve(newImageName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file:" + e.getMessage());
		}
		User user = new User(
			    username,
			    firstname,
			    lastname,
			    email,
			    country,
			    passwordEncoder.encode(password),
			    newImageName
			);		userRepository.save(user);
		
		return user;
	}*/

	@Override
	public User updateUser(
	        MultipartFile file,
	        String username,
	        String firstname,
	        String lastname,
	        String email,
	        String country,
	        String password,
	        long id) {
	
	    return userRepository.findById(id).map(user -> {
	
	        // CHECK USERNAME UNIQUENESS (exclude current user)
	        if (!user.getUsername().equals(username)
	                && userRepository.existsByUsername(username)) {
	            throw new RuntimeException("Error: Username already exists!");
	        }
	
	        // CHECK EMAIL UNIQUENESS (exclude current user)
	        if (!user.getEmail().equals(email)
	                && userRepository.existsByEmail(email)) {
	            throw new RuntimeException("Error: Email already exists!");
	        }
	
	        // STEP 1: update image if provided
	        String newImageName = user.getProfilePic();
	
	        if (file != null && !file.isEmpty()) {
	            fileStorageService.deleteFile(user.getProfilePic());
	            newImageName = fileStorageService.saveFile(file);
	        }
	
	        // STEP 2: update fields
	        user.setUsername(username);
	        user.setEmail(email);
	        user.setFirstname(firstname);
	        user.setLastname(lastname);
	        user.setCountry(country);
	        user.setPassword(passwordEncoder.encode(password));
	        user.setProfilePic(newImageName);
	
	        return userRepository.save(user);
	
	    }).orElseThrow(() ->
	            new IllegalArgumentException("UserId " + id + " not found"));
	}

	@Override
	public void deleteUserById(long id) {
		this.userRepository.deleteById(id);
		
	}
	

}
