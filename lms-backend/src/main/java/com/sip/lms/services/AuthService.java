package com.sip.lms.services;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.entities.Role;
import com.sip.lms.models.requests.LoginRequest;
import com.sip.lms.models.requests.SignupRequest;
import com.sip.lms.models.responses.JwtResponse;
import com.sip.lms.models.responses.MessageResponse;

import jakarta.validation.Valid;

public interface AuthService {
	
    ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest, MultipartFile imageFile);
    
    JwtResponse  authenticateUser(@Valid LoginRequest loginRequest);

}
