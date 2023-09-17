package com.practice.ecommerce.controllers;

import com.practice.ecommerce.exceptions.UserNotFoundException;
import com.practice.ecommerce.payloads.LoginCredential;
import com.practice.ecommerce.payloads.UserDTO;
import com.practice.ecommerce.security.JWTUtils;
import com.practice.ecommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    // public ResponseEntity<UserDTO> registerHandler(@Valid @RequestBody UserDTO
    // userDTO) throws UserNotFoundException {
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO userDTO)
            throws UserNotFoundException {
        UserDTO registerUser = userService.registerUser(userDTO);

        String token = jwtUtils.generateToken(registerUser.getEmail());

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("user data", registerUser);

        // return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginHandler(LoginCredential loginCredential) {

        System.out.println("authCredential");
        UsernamePasswordAuthenticationToken authCredential =
                new UsernamePasswordAuthenticationToken(loginCredential.getEmail(), loginCredential.getPassword());


        authenticationManager.authenticate(authCredential);

        String token = jwtUtils.generateToken(loginCredential.getEmail());
        UserDTO userFromDB = userService.login(loginCredential);

        Map<String, Object> response = new HashMap<>();

        response.put("token", token);
        response.put("user data", userFromDB);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
