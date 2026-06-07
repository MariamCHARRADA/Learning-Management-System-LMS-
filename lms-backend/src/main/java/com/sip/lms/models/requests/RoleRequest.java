package com.sip.lms.models.requests;

public class RoleRequest {
	String roleValue;
	
	public RoleRequest(int id, String roleValue) {
		super();
		this.roleValue = roleValue;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

}
