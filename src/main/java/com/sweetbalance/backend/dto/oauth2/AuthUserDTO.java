package com.sweetbalance.backend.dto.oauth2;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthUserDTO {

    private Long userId;

    private String role;

    private String nickname;

    private String username;
}