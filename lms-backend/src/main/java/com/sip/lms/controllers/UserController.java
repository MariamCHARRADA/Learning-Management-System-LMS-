package com.sip.lms.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.sip.lms.entities.User;
import com.sip.lms.services.UserService;



@RestController
@RequestMapping("/users")
public class UserController {		
	
	@Autowired //IOC inversion of control = injecting dependencies
	UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(this.userService.getAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/")	
	public User create(@RequestParam(name="imageFile") MultipartFile file,
			@RequestParam("username") String username,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email, 
			@RequestParam("country") String country
			) throws IOException {
		return userService.addUser(file, username,firstname,lastname, email, country); //should be in order
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable long id) {
	
		Optional<User> opt = this.userService.getUserById(id);
	
		if (opt.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(opt.get(), HttpStatus.OK); 
	}
	
	@DeleteMapping("/{id}") // id is a variable
	public ResponseEntity<User> deleteUserById(@PathVariable long id) { // id is retrieved from the path URL
	
		Optional<User> opt = this.userService.getUserById(id);
	
		if (opt.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			this.userService.deleteUserById(id);
			return new ResponseEntity<>(opt.get(), HttpStatus.OK);
		}
	}
	
	@PutMapping("/{id}")
	public User updateUser(@RequestParam(name="imageFile",required = false) MultipartFile file,
	        @PathVariable long id,
			@RequestParam("username") String username,
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email, 
			@RequestParam("country") String country
			) throws IOException {
		
		return userService.updateUser(file,username,firstname,lastname, email, country,id);
	}


}
