package com.example.calendar.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class User {

    //step 3
    private Long id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
