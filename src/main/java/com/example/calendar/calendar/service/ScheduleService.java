package com.example.calendar.calendar.service;

import com.example.calendar.calendar.dto.ScheduleRequestDto;
import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto, Long userId);

    ScheduleResponseDto getScheduleById(Long id);

    List<ScheduleResponseDto> getSchedulesByAuthor(String author);

    void updateSchedule(ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, String password);
}
