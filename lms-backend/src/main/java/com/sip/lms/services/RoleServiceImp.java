package com.sip.lms.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.sip.lms.entities.Role;
import com.sip.lms.repositories.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {
	@Autowired
	RoleRepository roleRepository;
	

	@Override
	public List<Role> getAllRoles() {
		return (List<Role>) this.roleRepository.findAll();
	}

	
	@Override
	public Role addRole(Role role) {
		return this.roleRepository.save(role);
	}


	@Override
	public void deleteRoleById(int id) {
		this.roleRepository.deleteById(id);

	}
	
	@Override
	public Optional<Role> getRoleById(int id) {
		return this.roleRepository.findById(id);

	}
}

