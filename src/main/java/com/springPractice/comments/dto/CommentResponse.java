package com.springPractice.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private final Long CommentId;
    private final String username;
    private final String comment_text;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
