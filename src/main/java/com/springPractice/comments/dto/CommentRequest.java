package com.springPractice.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequest {

    @NotBlank(message = "댓글 내용은 필수 입력 값입니다.")
    @Size(max = 255, message = "255자까지 입력 가능합니다.")
    private final String commentText;
}
