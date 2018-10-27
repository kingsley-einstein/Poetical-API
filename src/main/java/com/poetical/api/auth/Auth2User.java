package com.poetical.api.auth;

import com.poetical.api.models.User;

public class Auth2User extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 1L;

    public Auth2User(User user) {
        super(user.getUsername(), user.getPassword(), user.getGrantedAuthoritiesList());
    }
}