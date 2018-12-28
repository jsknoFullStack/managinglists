package com.jskno.managinglistsbe.security.service;

import com.jskno.managinglistsbe.security.jwt.JwtUserFactory;
import com.jskno.managinglistsbe.security.persistence.User;
import com.jskno.managinglistsbe.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
        return JwtUserFactory.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.getById(id);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("No user found with userId '%s'.", id));
        }
        return JwtUserFactory.create(user);
    }


}
