package com.sip.lms.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.sip.lms.entities.EnumRole;
import com.sip.lms.entities.Role;
import com.sip.lms.entities.User;
import com.sip.lms.jwt.JwtUtils;
import com.sip.lms.models.requests.LoginRequest;
import com.sip.lms.models.requests.SignupRequest;
import com.sip.lms.models.responses.JwtResponse;
import com.sip.lms.models.responses.MessageResponse;
import com.sip.lms.repositories.RoleRepository;
import com.sip.lms.repositories.UserRepository;

import jakarta.validation.Valid;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder encoder;
    
    private Set<Role> assignRoles(Set<String> strRoles) {

        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {

            Role stagiaireRole =
                    roleRepository.findByName(EnumRole.STAGIAIRE)
                    .orElseThrow(() ->
                            new RuntimeException("Role not found"));

            roles.add(stagiaireRole);

            return roles;
        }

        strRoles.forEach(role -> {

            switch (role.toLowerCase()) {

            case "superadmin":
                roles.add(
                    roleRepository.findByName(EnumRole.SUPER_ADMIN)
                    .orElseThrow(() ->
                        new RuntimeException("Role not found")));
                break;

            case "formateur":
                roles.add(
                    roleRepository.findByName(EnumRole.FORMATEUR)
                    .orElseThrow(() ->
                        new RuntimeException("Role not found")));
                break;

            default:
                roles.add(
                    roleRepository.findByName(EnumRole.STAGIAIRE)
                    .orElseThrow(() ->
                        new RuntimeException("Role not found")));
            }
        });

        return roles;
    }

    @Override
    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest, MultipartFile imageFile) {
    	
        // Username check
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username already exists!"));
        }

        // Email check
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email already exists!"));
        }
        
        String imageName = fileStorageService.saveFile(imageFile);

        // Create user
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getFirstname(),
                signUpRequest.getLastname(),
                signUpRequest.getEmail(),
                signUpRequest.getCountry(),
                encoder.encode(signUpRequest.getPassword()),
                imageName
        );

        user.setRoles(assignRoles(signUpRequest.getRole()));

        userRepository.save(user);

        return ResponseEntity.ok(
                new MessageResponse("User registered successfully!")
        );
    }
    
    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

    	  //1-get Authentication
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

         //2-get token
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        //3-get User details
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
         //4-get roles
        List<String> roles = userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());

        return new JwtResponse(jwt, 
                             userDetails.getId(), 
                             userDetails.getUsername(), 
                             userDetails.getEmail(), 
                             roles);
      }
    
    
}