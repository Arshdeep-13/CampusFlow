package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.LoginRequest;
import com.arshdeep.campusflow.dto.request.SignInRequest;
import com.arshdeep.campusflow.service.AuthService;
import lombok.RequiredArgsConstructor;
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
    public String signIn(@RequestBody SignInRequest signInRequest){
        return authService.signIn( signInRequest );
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.logIn( loginRequest );
    }
}
