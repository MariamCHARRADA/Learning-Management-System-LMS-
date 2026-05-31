package com.sip.lms.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.entities.User;
import com.sip.lms.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	UserRepository userRepository;
	
	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>)this.userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(long id) {
		// TODO Auto-generated method stub
		return this.userRepository.findById((int) id);
	}
	
	@Override 
	public User addUser(MultipartFile file, String username, String firstname, String lastname, String email, String country) {
		String newImageName = getSaltString().concat(file.getOriginalFilename());
		
		try {
			Files.copy(file.getInputStream(), this.root.resolve(newImageName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file:" + e.getMessage());
		}
		User user = new User(username, firstname, lastname, email, country, newImageName);
		userRepository.save(user);
		
		return user;
	}

	@Override
	public User updateUser(MultipartFile file, String username, String firstname, String lastname, String email, String country, long id) {

		return userRepository.findById((int) id).map(user -> {

			
			if(file!=null)
			{
			// STEP 1 : delete Old Image from server
			String OldImageName = user.getProfilePic();


			try {
				File f = new File(this.root + "/" + OldImageName); // file to be delete
				if (f.delete()) // returns Boolean value
				{
					System.out.println(f.getName() + " deleted"); // getting and printing the file name
				} else {
					System.out.println("failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			}

			/// STEP 2 : Upload new image to server
			String newImageName = user.getProfilePic();
			if(file!=null) {
			    newImageName = getSaltString().concat(file.getOriginalFilename());
			try {
				Files.copy(file.getInputStream(), this.root.resolve(newImageName));
			} catch (Exception e) {
				throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
			}
			}

			/// STEP 3 : Update user in database
			user.setUsername(username);
			user.setEmail(email);
			user.setFirstname(firstname);
			user.setLastname(lastname);
			user.setCountry(country);
			user.setProfilePic(newImageName);
			return userRepository.save(user);
		}).orElseThrow(() -> new IllegalArgumentException("UserId " + id + " not found"));
	}

	@Override
	public void deleteUserById(long id) {
		this.userRepository.deleteById((int) id);
		
	}
	
	//random string for the image name
	protected static String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
		}


}
