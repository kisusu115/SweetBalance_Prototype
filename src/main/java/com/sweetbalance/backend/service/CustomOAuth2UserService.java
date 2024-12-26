package com.sweetbalance.backend.service;

import com.sweetbalance.backend.dto.oauth2.SocialUserDTO;
import com.sweetbalance.backend.dto.oauth2.CustomOAuth2User;
import com.sweetbalance.backend.dto.oauth2.GoogleResponse;
import com.sweetbalance.backend.dto.oauth2.NaverResponse;
import com.sweetbalance.backend.dto.oauth2.OAuth2Response;
import com.sweetbalance.backend.entity.User;
import com.sweetbalance.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sweetbalance.backend.enums.user.Role.ROLE_USER;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private UserRepository userRepository;

    @Autowired
    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println("OAuth2User 내부 값 확인: "+oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();

        Optional<User> existData = userRepository.findByUsername(username);

        // DB에 존재하지 않는 신규 유저일 때
        if (existData.isEmpty()) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setNickname(oAuth2Response.getName());
            newUser.setEmail(oAuth2Response.getEmail());
            newUser.setRole(ROLE_USER);

            userRepository.save(newUser);

            SocialUserDTO socialUserDTO = new SocialUserDTO();
            socialUserDTO.setUsername(username);
            socialUserDTO.setNickname(oAuth2Response.getName());
            socialUserDTO.setRole(ROLE_USER.getValue());

            return new CustomOAuth2User(socialUserDTO);
        }

        // DB에 이미 존재하는 유저일 때
        else {
            User existUser = existData.get();
            existUser.setEmail(oAuth2Response.getEmail());
            existUser.setNickname(oAuth2Response.getName()); //닉네임을 로그인 마다 업데이트 하는 건 아마 삭제될 듯

            userRepository.save(existUser);

            SocialUserDTO socialUserDTO = new SocialUserDTO();
            socialUserDTO.setUsername(existUser.getUsername());
            socialUserDTO.setNickname(oAuth2Response.getName());
            socialUserDTO.setRole(existUser.getRole().getValue());

            return new CustomOAuth2User(socialUserDTO);
        }
    }
}