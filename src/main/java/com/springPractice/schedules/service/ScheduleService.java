package com.springPractice.schedules.service;

import com.springPractice.schedules.dto.ScheduleRequest;
import com.springPractice.schedules.dto.ScheduleResponse;
import com.springPractice.schedules.entity.Schedule;
import com.springPractice.schedules.repository.ScheduleRepository;
import com.springPractice.users.entity.User;
import com.springPractice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
// 서비스 임을 명시
@RequiredArgsConstructor
// final , @NonNull 이 붙은 매개변수가 있는 생성자를 자동 생성
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    // @RequiredArgsConstructor 이거 없으면 아래처럼 생성자 정의 필요
    //   public ScheduleService(ScheduleRepository scheduleRepository) {
    //       this.scheduleRepository = scheduleRepository;
    //   }

    private final UserRepository userRepository;
    // 유저 매핑 후 추가

    // 일정 생성 //
    @Transactional
    // 작업을 한 단위로 묶어주는 역할
    // 한 단위가 모두 성공되면 실행되고, 하나라도 안되면 롤백
    // 실패임에도 중간에 하나가 진행되는것을 방지 (원자성)
     public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest, Long userId) {
        // userId 조회
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );

        // " 엔티티 객체 생성 " : 요청 데이터 저장
        Schedule schedule = new Schedule(
        // 엔티티 객체 생성 -> title, content 값 세팅  => DB 저장X
        // 요청내용을 schedule 에 저장
                scheduleRequest.getTitle(),
                scheduleRequest.getContent(),
                user
        );

        // " 엔티티 저장 " : 요청 데이터 + DB 자동 생성된 데이터
        Schedule savedSchedule = scheduleRepository.save(schedule);
        /* - scheduleRepository가 jpa 상속 -> 기본 제공되는 save() 메서드 사용 가능
           - save 메서드 호출 (요청내용 전달) -> DB 에서 insert 쿼리 실행
           - 이 때 전달된 엔티티(schedule)에는 title, content 값만 있고, 나머지는 null
              -> id, createdAt, updatedAt 는 엔티티에 붙은 어노테이션 덕분에 자동생성됨
           - DB 저장 후, id, createdAt, updatedAt 자동생성된 값이 엔티티에 반영
           - 반환값(savedSchedule)에는 요청 데이터(title, content) + DB 에서 자동생성된 값 모두 담김
         */

        return new ScheduleResponse(
        // [엔티티] -> [DTO] 변환
        /*    1. 내부 구조 노출 위험 감소
                  - 엔티티는 DB 구조와 직접 연결 -> 그대로 반환 시 보안/유지보수 문제 발생
              2. 외부 응답 전용 객체로 사용
              3. 엔티티와 분리
                  - 엔티티 변경 있어도 API 응답 안정적으로 유지 가능
              4. 명확한 의도 표현
                  - DTO 클래스 이름 설정 가능
         */
        // 새로운 DTO 객체 생성하여 반환 (응답용)
        // 반환값이 저장된 savedSchedule 요소들 모두 반환
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getUpdatedAt()
        );
    }

    // 일정 전체 조회 //
    @Transactional(readOnly = true)
    // (readOnly = true) : "읽기 전용" -> 엔티티 수정해도 DB에 반영 안됨 (변경 감지 비활성화)
    // INSERT/UPDATE/DELETE 실행 시 = 쿼리 막거나 무시 가능
    public List<ScheduleResponse> getAllSchedules() {

        List<Schedule> schedules = scheduleRepository.findAll();
        // 요청데이터가 없기 때문에 엔티티 객체 생성 과정 불필요
        // DB에 저장되어 있는 일정만 불러오면 됨

        return schedules.stream()
        // List<Schedule> -> Stream<Schedule> 로 변환
        // Stream 이용하면 map, filter, collect 같은 함수형 연산 가능
                .map(
                // map : "변환" 연산 (Stream API 에서 제공)
                // Stream의 각 원소를 다른 형태로 하나씩 변환할 때 사용
                // Stream<Schedule> -> Stream<ScheduleResponse> 변환
                schedule -> new ScheduleResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getCreatedAt(),
                        schedule.getUpdatedAt()
                        // Schedule 엔티티 객체에서 꺼내온 값
                        // 즉, 이 값들을 생성자의 인자로 넣어 ScheduleResponse 라는 DTO 객체 생성
                        // 모든 Schedule 객체마다 ScheduleResponse 새로 만들고
                )
        ).toList();
        // 최종적으로 List<ScheduleResponse> 로 반환
        // 즉, 엔티티를 DTO로 변환하여 응답
    }

    // 일정 단건 조회 //
    @Transactional(readOnly = true)
    public ScheduleResponse getSchedule(Long scheduleId) {

        // 엔티티 저장소(DB)에서 꺼내온 Schedule 객체를 schedule 변수에 담음
        // ( scheduleRepository의 findById() 메서드 호출해서 꺼내옴 )
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 일정이 없습니다.")
        );

        // 엔티티(Schedule) -> DTO(ScheduleResponse)로 변환
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }
    /*   [ orElseThrow 미작성 시 오류 나는 이유 ]
            - findById()  => Optional<T> findById(Id id)
              즉, 반환 타입이 Optional<Schedule> 이므로 Schedule 변환 필요 (변환 안하면 컴파일 오류 발생)

              1. 값 없으면 예외 던짐 -> 현재 위 단건 조회에서 사용
              2. 값 없으면 기본값 사용
                  Schedule schedule = scheduleRepository.findById(scheduleId)
                     .orElse(new Schedule());
     */

    // 일정 수정 //
    @Transactional
    public ScheduleResponse updateSchedule(Long loginUserId, Long scheduleId, ScheduleRequest scheduleRequest) {

        // 1. DB 에서 기존 엔티티 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 일정이 없습니다.")
        );

        // 본인 검증 추가
        if (!schedule.getUserId().equals(loginUserId)) {
            throw new IllegalArgumentException("본인이 작성한 일정만 수정할 수 있습니다.");
        }

        // 2. 수정 데이터 저장
        // -> Schedule 엔티티에 메서드 정의
        schedule.updateSchedule(
                scheduleRequest.getTitle(),
                scheduleRequest.getContent());

        // 3. DTO로 변환하여 반환
        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt()
        );
    }

    // 일정 삭제 //
    @Transactional
    public void deleteSchedule(Long scheduleId) {

        // 1. DB 에서 기존 엔티티 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalArgumentException()
        );

        // 2. 삭제
        scheduleRepository.delete(schedule);
    }
}
