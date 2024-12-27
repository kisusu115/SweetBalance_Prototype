package com.sweetbalance.backend.util;

import com.sweetbalance.backend.dto.CustomUserDetails;
import com.sweetbalance.backend.dto.oauth2.CustomOAuth2User;
import com.sweetbalance.backend.dto.oauth2.AuthUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            
        //특정 엔드포인트 요청에 대해 필터를 넘어가도록 따로 설정도 가능함 (정규표현식 사용)
        String requestUri = request.getRequestURI();

//        if (requestUri.matches("/api/users/sign-up") || requestUri.matches("/api/users/sign-in")) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        /*
        // 쿠키 방식 인증 시 사용 로직
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if (authorization == null) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization;
        */

        // 헤더 방식 인증 시 사용 로직
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 username과 role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        String userType = jwtUtil.getUserType(token);

        //userDTO를 생성하여 값 set
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setUsername(username);
        authUserDTO.setRole(role);

        Authentication authToken= null;

        if(userType.equals("basic")){
            CustomUserDetails customUserDetails = new CustomUserDetails(authUserDTO);

            //스프링 시큐리티 인증 토큰 생성
            authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        }

        if(userType.equals("social")){
            //UserDetails에 회원 정보 객체 담기
            CustomOAuth2User customOAuth2User = new CustomOAuth2User(authUserDTO);
            //스프링 시큐리티 인증 토큰 생성
            authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        }

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}