package com.springPractice.comments.service;

import com.springPractice.comments.dto.CommentRequest;
import com.springPractice.comments.dto.CommentResponse;
import com.springPractice.comments.entity.Comment;
import com.springPractice.comments.repository.CommentRepository;
import com.springPractice.schedules.entity.Schedule;
import com.springPractice.schedules.repository.ScheduleRepository;
import com.springPractice.users.entity.User;
import com.springPractice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 댓글 생성 //
    @Transactional
    public CommentResponse createComment(Long scheduleId, Long loginUserId, CommentRequest commentRequest) {

        // 1. 작성자 조회
        User user = userRepository.findById(loginUserId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        // 2. 일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 일정이 없습니다.")
        );

        // 3. 댓글 엔티티 생성
        // 엔티티에 추가
        Comment comment = new Comment(
                commentRequest.getCommentText(),
                user,
                schedule
        );

        // 4. 엔티티 저장
        Comment savedComment = commentRepository.save(comment);

        // 5. dto로 변환(반환)
        return new CommentResponse (
                savedComment.getId(),
                savedComment.getUser().getUsername(),
                savedComment.getComment_text(),
                savedComment.getCreatedAt(),
                savedComment.getCreatedAt()
        );
    }
}
