package com.example.calendar.calendar.repository;

import com.example.calendar.calendar.dto.ScheduleRequestDto;
import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto save(Schedule schedule);

    List<ScheduleResponseDto> findAll(String author);

    Schedule findByIdElseThrow(Long id);

    int update(Long id, String todo, String author, String password);

    int delete(Long id, String password);
}
