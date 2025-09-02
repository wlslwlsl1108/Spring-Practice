package com.springPractice.schedules.repository;

import com.springPractice.schedules.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 생략 가능 -> JpaRepository<Schedule,Long> 상속하면 이 기능이 적용되기 때문
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
