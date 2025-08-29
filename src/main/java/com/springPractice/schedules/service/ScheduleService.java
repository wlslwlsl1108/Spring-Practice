package com.springPractice.schedules.service;

import com.springPractice.schedules.dto.ScheduleRequest;
import com.springPractice.schedules.dto.ScheduleResponse;
import com.springPractice.schedules.entity.Schedule;
import com.springPractice.schedules.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 생성 //
    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {

        Schedule schedule = new Schedule(
                scheduleRequest.getTitle(),
                scheduleRequest.getContent()
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );

    }

}
