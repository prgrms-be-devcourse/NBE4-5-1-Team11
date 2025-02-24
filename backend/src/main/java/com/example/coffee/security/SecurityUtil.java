package com.example.coffee.security;

import com.example.coffee.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtil {
    // 로그인된 상황에서 현재 유저
    /*public static User getCurrentUserWithLogin() {
        try {
            return getUser();
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("User not Found");
        }
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
    }*/

    // 로그인 여부를 모르는 상황에서 현재 유저 찾기 -> Optional User
    /*public static Optional<User> getOptUser() {
        return Optional.ofNullable(getCurrentUserOrNotLogin());
    }

    private static User getCurrentUserOrNotLogin() {
        try {
            return getUser();
        } catch (Exception e) {
            return null;
        }
    }*/

    // 로그인된 상황에서 현재 유저 반환 (예외 발생 시 "User not Found")
    public static User getCurrentUserWithLogin() {
        try {
            return getUser();
        } catch (Exception e) {
            throw new IllegalArgumentException("User not Found");
        }
    }

    // 로그인 여부와 관계없이 현재 유저 Optional로 반환
    public static Optional<User> getOptUser() {
        try {
            return Optional.ofNullable(getUser());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // SecurityContextHolder에서 User 가져오기
    private static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new IllegalArgumentException("User not Found");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthDetails)) {
            throw new IllegalArgumentException("User not Found");
        }

        return ((AuthDetails) principal).getUser();
    }
}
