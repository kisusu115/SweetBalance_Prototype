package com.sweetbalance.backend.service;

import com.sweetbalance.backend.dto.SignUpDTO;
import com.sweetbalance.backend.entity.Beverage;
import com.sweetbalance.backend.entity.User;
import com.sweetbalance.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void join(SignUpDTO signUpDTO){
        // 정규식 처리 등 가입 불가 문자에 대한 처리도 진행해주어야 한다.
        boolean userExists = userRepository.existsByUsername(signUpDTO.getUsername());
        if(userExists) return;

        User bCryptPasswordEncodedUser = makeBCryptPasswordEncodedUser(signUpDTO);
        userRepository.save(bCryptPasswordEncodedUser);
    }

    public Optional<User> findUserByUserId(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // 특정 사용자 ID로 음료 목록 조회
    public List<Beverage> findBeveragesByUserId(Long userId) {
        return userRepository.findBeveragesByUserId(userId);
    }

    private User makeBCryptPasswordEncodedUser(SignUpDTO signUpDTO){
        User user = signUpDTO.toActiveUser();
        String rawPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        return user;
    }
}