package com.epam.sample.dhsserver.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SampleAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            if (authentication instanceof SampleAuthentication) {
                return processAuthentication((SampleAuthentication) authentication);
            }
            authentication.setAuthenticated(false);
            return authentication;
        } catch (Exception e) {
            throw new AuthenticationServiceException("Error during authentication", e);
        }
    }

    private SampleAuthentication processAuthentication(SampleAuthentication authentication) {
        authentication.setAuthenticated(true);
        return authentication;
    }

}
