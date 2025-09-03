package com.springPractice.users.service;

import com.springPractice.common.config.PasswordEncoder;
import com.springPractice.users.dto.*;
import com.springPractice.users.entity.User;
import com.springPractice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 생성 //
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        // 비밀번호 인코딩 //
        String encodePassword = passwordEncoder.encode(userRequest.getPassword());
        // 평문 비밀번호 -> 인코딩된 문자열 (변환)

        //1. 엔티티 객체 생성
        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                encodePassword
                // 인코딩한 password 값을 User 엔티티의 생성자에 전달
                // User 엔티티의 password 값이 인코딩값으로 초기화
        );

        //2. 엔티티 저장 (DB 저장)
        User savedUser = userRepository.save(user);
        // DB에 password는 인코딩된 문자열로 저장

        //3. dto로 변환
        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }

    // 유저 로그인 //
    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {

        // 1. 이메일로 유저 조회 (탈퇴 포함)
        // User 레포지토리에 findByEmail 추가
        User user = userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 이메일이 없습니다.")
        );

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."
            );
        }

        // 3. 로그인 성공
        return new UserLoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail()
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
