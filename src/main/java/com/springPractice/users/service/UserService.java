package com.springPractice.users.service;

import com.springPractice.users.dto.UserRequest;
import com.springPractice.users.dto.UserResponse;
import com.springPractice.users.entity.User;
import com.springPractice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 유저 전체 조회 //
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        // 1. 엔티티 저장 (DB 저장)
        List<User> users = userRepository.findAll();

        // 2. dto로 변환
        return users.stream()
                .map(
                        user -> new UserResponse(
                                user.getId(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getCreatedAt(),
                                user.getUpdatedAt()
                        )
                ).toList();
    }
}
