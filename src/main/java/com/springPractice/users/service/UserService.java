package com.springPractice.users.service;

import com.springPractice.users.dto.UserUpdateRequest;
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

    // 유저 단건 조회 //
    @Transactional(readOnly = true)
    public UserResponse getUser(Long userId) {

        // 1. 엔티티 저장 (DB 저장)
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // 유저 수정 //
    @Transactional
    public UserResponse updateUser(Long userId, UserUpdateRequest userUpdateRequest) {

        // 1. DB 에서 기본 엔티티 조회
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        // 2. 수정 데이터 저장
        // userRequest 에서 수정 데이터를 getter 로 꺼내오고,
        // 엔티티의 updateUser 메서드를 통해 기존 username을 전달 받은 데이터로 변경
        // => user 엔티티에 변경된 데이터가 저장
        user.updateUser(
                userUpdateRequest.getUsername()
        );

        // 3. dto로 변환/반환
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void deleteUser(Long userId) {

        // 1. DB 에서 기본 엔티티 조회
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        // 2. 삭제
        userRepository.delete(user);
    }
}
