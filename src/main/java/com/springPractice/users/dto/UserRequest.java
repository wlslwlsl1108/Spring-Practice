package com.springPractice.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {

    @NotBlank(message = "유저이름은 필수 입력 값입니다.")
    @Size(max=50, message = "50자까지 입력 가능합니다.")
    private final String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Size(max=100, message = "100자까지 입력 가능합니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min=8, message = "비밀번호는 공백 없이 8자 이상 입력해주세요.")
    @Pattern(
            regexp = "^(?=\\S+$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\p{Punct}).*$",
            // 공백없이, 소문자 1개 이상, 대문자 1개 이상, 숫자 1개 이상, 특수문자 1개 이상
            // (?=\S+$) : 공백이 아닌 문자  ,  (?=\s+$) : 공백 문자
            // 대소문자 주의!!
            message = "비밀번호는 공백없이 대/소문자, 숫자, 특수문자를 포함하여야 합니다."
    )
    private final String password;

}
/*
     [ @NotNull ]
        - null 값만 막음 -> null 이면 에러 발생
        - 하지만, 빈 문자열("")이나 공백(" ") 값은 통과

     [ @NotBlank ]
        - '문자열 전용' 검증 어노테이션
        - null,  빈 문자열(""), 공백(" ") 전부 막음

     [ @NotEmpty ]
        - null,  빈 문자열("") 막음
        - 공백(" ")은 허용

     => ERD : NOT NULL  => DB 레벨 제약 조건

        즉, ERD 에서 NOT NULL 조건을 걸었다고해서
            코드에서도 @NotNull 만 쓸 수 있는게 아님

        위 어노테이션은 " 애플리케이션 레벨 검증 "

        그러므로 ERD 에서 "NOT NULL" 제약조건 사용하고,
        DTO 에서 @NotBlank 나 @NorEmpty 사용해도 문제 없다.
 */

