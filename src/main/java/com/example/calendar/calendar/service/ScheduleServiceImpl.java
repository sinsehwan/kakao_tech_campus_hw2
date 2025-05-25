package com.example.calendar.calendar.service;

import com.example.calendar.calendar.dto.ScheduleRequestDto;
import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.entity.Schedule;
import com.example.calendar.calendar.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, Long userId) {
        Schedule schedule = new Schedule(userId, requestDto.getTodo(), requestDto.getAuthor(), requestDto.getPassword());

        return scheduleRepository.save(schedule);
    }

    // 단건 조회
    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule result = scheduleRepository.findByIdElseThrow(id);

        return new ScheduleResponseDto(result.getId(), result.getUserId(), result.getTodo(), result.getAuthor(), result.getCreatedAt(), result.getModifiedAt());
    }

    // 작성자 일정 수정일 내림차순으로 반환
    public List<ScheduleResponseDto> getSchedulesByAuthor(String author){

        if(author == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "author is essential value.");
        }

        return scheduleRepository.findAll(author);
    }

    public void updateSchedule(ScheduleRequestDto requestDto) {

        // step 5
        if(requestDto.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "schedule id is essential value.");
        }

        if(requestDto.getTodo() == null || requestDto.getAuthor() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value check");
        }

        int updatedNum = scheduleRepository.update(
                requestDto.getId(),
                requestDto.getTodo(),
                requestDto.getAuthor(),
                requestDto.getPassword()
        );

        if (updatedNum == 0) {
            throw new IllegalArgumentException("modify failed: ID or password is not correct");
        }
    }

    // 일정 삭제
    public void deleteSchedule(Long id, String password) {
        int deletedNum = scheduleRepository.delete(id, password);
        // step 5
        if(deletedNum == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "modify failed: ID or password is not correct");
        }
    }
}
