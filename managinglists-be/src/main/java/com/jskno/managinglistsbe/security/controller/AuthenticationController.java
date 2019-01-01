package com.jskno.managinglistsbe.security.controller;

import com.jskno.managinglistsbe.security.domain.LoginRequest;
import com.jskno.managinglistsbe.security.domain.LoginResponse;
import com.jskno.managinglistsbe.security.jwt.JwtTokenProvider;
import com.jskno.managinglistsbe.security.jwt.JwtUser;
import com.jskno.managinglistsbe.security.service.CustomUserDetailsService;
import com.jskno.managinglistsbe.security.utils.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        //UserDetails userDetails3 = (JwtUser) authentication.getPrincipal();
        //JwtUser userDetails4 = (JwtUser) authentication.getPrincipal();

        String token = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(userDetails);

        return new LoginResponse(true, token);
    }
}
