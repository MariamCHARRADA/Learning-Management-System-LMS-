package com.sip.lms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 20)
	private String firstname;
	
	@NotBlank
	@Size(max = 20)
	private String lastname;
	
	@Email
	@NotBlank
	@Size(max = 50)
	private String email;

	@NotBlank
	@Size(max = 20)
	private String country;
	
	@Column(name = "profilePic")
	private String profilePic;
	
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 20) String firstname,
			@NotBlank @Size(max = 20) String lastname, @Email @NotBlank @Size(max = 50) String email,
			@NotBlank @Size(max = 20) String country,
			@NotBlank(message = "Profile picture is mandatory") String profilePic) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.country = country;
		this.profilePic = profilePic;
	}

	public User() {
		super();
	}


}
