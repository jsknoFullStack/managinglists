package com.jskno.managinglistsbe.servicies;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {

    Authentication getAuthentication();
    UserDetails getAuthenticatedUser();
    String getAuthenticatedUsername();
}
