package com.sip.lms.controllers;



import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.models.requests.LoginRequest;
import com.sip.lms.models.requests.SignupRequest;
import com.sip.lms.models.responses.MessageResponse;
import com.sip.lms.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(
            @RequestParam String username,
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String country,
            @RequestParam String password,
            @RequestParam(required = false) Set<String> role,
            @RequestParam(required = false) MultipartFile imageFile) {
    	
        SignupRequest signUpRequest = new SignupRequest();

        signUpRequest.setUsername(username);
        signUpRequest.setFirstname(firstname);
        signUpRequest.setLastname(lastname);
        signUpRequest.setEmail(email);
        signUpRequest.setCountry(country);
        signUpRequest.setPassword(password);
        signUpRequest.setRole(role);

        return authService.registerUser(signUpRequest, imageFile);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(
                authService.authenticateUser(loginRequest));
    }
}