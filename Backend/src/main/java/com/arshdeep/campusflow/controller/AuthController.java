package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.LoginRequest;
import com.arshdeep.campusflow.dto.request.SignInRequest;
import com.arshdeep.campusflow.dto.response.LoginResponse;
import com.arshdeep.campusflow.dto.response.SignInResponse;
import com.arshdeep.campusflow.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest){
        com.arshdeep.campusflow.entity.User user = authService.signIn(signInRequest);
        
        if (user == null) {
            SignInResponse response = SignInResponse.builder()
                    .message("User already exists")
                    .success(false)
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        
        SignInResponse response = SignInResponse.builder()
                .message("User created successfully")
                .success(true)
                .userId(user.getId())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        String token = authService.logIn(loginRequest);
        
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .message("Login successful")
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
