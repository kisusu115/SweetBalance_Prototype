package com.sweetbalance.backend.dto.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final AuthUserDTO authUserDTO;

    public CustomOAuth2User(AuthUserDTO authUserDTO) {

        this.authUserDTO = authUserDTO;
    }

    // 서드파티별로 Response 구조가 달라 사용 X
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return authUserDTO.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return authUserDTO.getNickname();
    }

    public String getUsername() {
        return authUserDTO.getUsername();
    }
}