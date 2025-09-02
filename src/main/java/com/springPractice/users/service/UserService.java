package com.springPractice.users.service;

import com.springPractice.users.dto.UserRequest;
import com.springPractice.users.dto.UserResponse;
import com.springPractice.users.entity.User;
import com.springPractice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성 //
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        //1. 엔티티 객체 생성
        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword()
        );

        //2. 엔티티 저장 (DB 저장)
        User savedUser = userRepository.save(user);

        //3. dto로 변환
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }
}
