package com.example.calendar.calendar.controller;

import com.example.calendar.calendar.dto.ScheduleRequestDto;
import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    // validation check
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto requestDto,
            @RequestParam Long userId
            ){

        ScheduleResponseDto response = scheduleService.saveSchedule(requestDto, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 일정 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto response = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 해당 작성자의 전체 일정을 수정일 기준 내림차순으로 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(
            @RequestParam String author
    )
    {
        List<ScheduleResponseDto> schedules = scheduleService.getSchedulesByAuthor(author);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // 일정 수정
    @PutMapping
    public ResponseEntity<Void> updateSchedule(@RequestBody ScheduleRequestDto requestDto) {
        scheduleService.updateSchedule(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestParam String password
    ) {
        scheduleService.deleteSchedule(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
