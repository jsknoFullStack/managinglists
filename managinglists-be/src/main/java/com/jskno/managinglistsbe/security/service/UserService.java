package com.jskno.managinglistsbe.security.service;

import com.jskno.managinglistsbe.security.exceptions.UsernameAlreadyExistsException;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new UsernameAlreadyExistsException("Username '" + user.getUsername() + "' already exists");
        }
    }
}
