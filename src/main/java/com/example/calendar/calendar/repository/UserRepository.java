package com.example.calendar.calendar.repository;

import com.example.calendar.calendar.entity.User;

public interface UserRepository {

    User findById(Long id);
}
