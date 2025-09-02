package com.springPractice.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateRequest {

    @NotBlank(message = "유저이름은 필수 입력 값입니다.")
    @Size(max=50, message = "50자까지 입력 가능합니다.")
    private final String username;
}
