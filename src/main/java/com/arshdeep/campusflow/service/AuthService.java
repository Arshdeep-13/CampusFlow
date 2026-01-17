package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.dto.request.LoginRequest;
import com.arshdeep.campusflow.dto.request.SignInRequest;
import com.arshdeep.campusflow.entity.User;
import com.arshdeep.campusflow.repository.UserRepository;
import com.arshdeep.campusflow.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    public String signIn(SignInRequest signInRequest){
        Optional<User> user = userRepository.findByUsername(signInRequest.getUsername());

        if( user.isPresent() ){
            return "User already exists";
        }

        User newUser = User.builder()
                .username(signInRequest.getUsername())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .role(signInRequest.getRole())
                .build();

        userRepository.save(newUser);

        return "User created successfully";
    }

    public String logIn(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String jwtToken = authUtil.generateBearerToken(user);

        return jwtToken;
    }
}
