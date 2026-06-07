package com.sip.lms.services;

import java.util.List;
import java.util.Optional;

import com.sip.lms.entities.Role;

public interface RoleService {
	public void deleteRoleById(int id);
	public List<Role> getAllRoles();
	public Role addRole(Role r);
	public Optional<Role> getRoleById(int id);
}
