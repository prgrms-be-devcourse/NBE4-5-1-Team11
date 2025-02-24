package com.example.coffee.security;

import com.example.coffee.user.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class securityUtil {
    public static User getCurrentUserWithLogin() {
        try {
            return getUser();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("User not Found");
        }
    }

    public static User getCurrentUserOrNotLogin() {
        try {
            return getUser();
        } catch (Exception e) {
            return null;
        }
    }

    public static Optional<User> getOptUser() {
        return Optional.ofNullable(getCurrentUserOrNotLogin());
    }

    private static User getUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof String) {
            throw new IllegalArgumentException("User not Found");
        }

        AuthDetails authDetails = (AuthDetails) principal;

        return authDetails.getUser();
    }
}
