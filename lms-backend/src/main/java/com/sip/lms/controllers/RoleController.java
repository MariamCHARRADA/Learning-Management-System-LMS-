package com.sip.lms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.lms.entities.Role;
import com.sip.lms.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	@GetMapping("/")
	public ResponseEntity<List<Role>> getAllRoles() {
		return new ResponseEntity<>(this.roleService.getAllRoles(), HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Role> addRole(@RequestBody Role r) {
		return new ResponseEntity<>(this.roleService.addRole(r), HttpStatus.CREATED); 
	} 
	
	@GetMapping("/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable int id) {
		Optional<Role> opt = this.roleService.getRoleById(id);

		if (opt.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<>(opt.get(), HttpStatus.OK); 
	}

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Role> deleteRoleById(@PathVariable int id) {
		Optional<Role> opt = this.roleService.getRoleById(id);

		if (opt.isEmpty())
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		else {
			this.roleService.deleteRoleById(id);
			return new ResponseEntity<>(opt.get(), HttpStatus.OK);
		}
	}

}
