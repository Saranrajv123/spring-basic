package com.practice.ecommerce.services;

import com.practice.ecommerce.entities.User;
import com.practice.ecommerce.exceptions.UserNotFoundException;
import com.practice.ecommerce.payloads.LoginCredential;
import com.practice.ecommerce.payloads.UserDTO;
import com.practice.ecommerce.repository.RoleRepository;
import com.practice.ecommerce.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String firstName) {
        User isUserFound = userRepository.existsByFirstName(firstName);

        System.out.println(isUserFound);


        return null;


    }

    public UserDTO registerUser(UserDTO userDTO) {
        try {

            User user = modelMapper.map(userDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registerUser = userRepository.save(user);

            userDTO = modelMapper.map(registerUser, UserDTO.class);
            return userDTO;

        } catch (DataIntegrityViolationException err) {
            throw new DataIntegrityViolationException("User not found with emailid " + userDTO.getEmail());

        }

    }

    public UserDTO login(LoginCredential loginCredential) {

        Optional<User> userFromDB = userRepository.findByEmail(loginCredential.getEmail());

        if (userFromDB.isPresent()) {
            User usr = userFromDB.get();
            System.out.println("user " + usr);
            return modelMapper.map(usr, UserDTO.class);


        }

//        if (userRepository.existsByEmail(loginCredential.getEmail())) {
//            throw new UserNotFoundException("User not found with email " + loginCredential.getEmail());
//        }

//
//        if (user == null) {
//            throw new UserNotFoundException("User not found with email " + loginCredential.getEmail());
//        }

        throw new UserNotFoundException("User not found with email " + loginCredential.getEmail());


    }
}
