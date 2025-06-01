package com.example.calendar.calendar.repository;

import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User findById(Long id) {
        String sql = "SELECT id, email, name, created_at, modified_at FROM `user` WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, UserRowMapper());
    }

    private RowMapper<User> UserRowMapper(){
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new User(
                        rs.getLong("id"),
                        rs.getString("email"),
                        rs.getString("name"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }
}
