package com.sweetbalance.backend.dto;

import com.sweetbalance.backend.entity.User;
import lombok.Getter;

import static com.sweetbalance.backend.enums.common.Status.ACTIVE;
import static com.sweetbalance.backend.enums.user.LoginType.BASIC;
import static com.sweetbalance.backend.enums.user.Role.ROLE_USER;

@Getter
public class SignUpDTO {
    private String username;

    private String password;

    private String email;

    public User toActiveUser(){
        return User.builder()
                .username(username).password(password).email(email)
                .role(ROLE_USER).loginType(BASIC).status(ACTIVE).build();
    }
}
