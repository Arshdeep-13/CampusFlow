package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.dto.request.LoginRequest;
import com.arshdeep.campusflow.dto.request.SignInRequest;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Teacher;
import com.arshdeep.campusflow.entity.User;
import com.arshdeep.campusflow.entity.type.RoleType;
import com.arshdeep.campusflow.repository.StudentRepository;
import com.arshdeep.campusflow.repository.TeacherRepository;
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
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public User signIn(SignInRequest signInRequest){
        Optional<User> user = userRepository.findByUsername(signInRequest.getUsername());

        if( user.isPresent() ){
            return null; // User already exists
        }

        // Only allow STUDENT signup - teachers and admins are created by admins
        User newUser = User.builder()
                .username(signInRequest.getUsername())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .role(RoleType.STUDENT) // Always set to STUDENT for signup
                .build();

        // Create student entity
        Student student = Student.builder()
                .user(newUser)
                .name(signInRequest.getUsername())
                .build();

        studentRepository.save(student);

        return userRepository.save(newUser);
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
