package com.example.calendar.calendar.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private Long userId; // 작성자의 식별자 (FK)
    private String todo;
    private String author;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule(Long userId, String todo, String author, String password){
        this.userId = userId;
        this.todo = todo;
        this.author = author;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
