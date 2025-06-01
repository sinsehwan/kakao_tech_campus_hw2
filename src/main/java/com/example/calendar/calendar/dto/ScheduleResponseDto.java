package com.example.calendar.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter @Setter
public class ScheduleResponseDto {
    private Long id;
    // step3
    private Long userId;
    private String todo;
    private String authorEmail; // user.email
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
