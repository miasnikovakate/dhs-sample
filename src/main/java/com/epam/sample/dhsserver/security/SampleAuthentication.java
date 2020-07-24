package com.epam.sample.dhsserver.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class SampleAuthentication extends AbstractAuthenticationToken {

    private String token;

    public SampleAuthentication(String token) {
        super(null);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
