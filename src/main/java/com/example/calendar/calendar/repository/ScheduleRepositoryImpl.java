package com.example.calendar.calendar.repository;

import com.example.calendar.calendar.dto.ScheduleResponseDto;
import com.example.calendar.calendar.entity.Schedule;
import com.example.calendar.calendar.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Override
    public ScheduleResponseDto save(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("todo", schedule.getTodo());
        parameters.put("user_id", schedule.getUserId());
        parameters.put("author", schedule.getAuthor());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", schedule.getCreatedAt());
        parameters.put("modified_at", schedule.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        User user = userRepository.findById(schedule.getUserId());

        return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getTodo(), user.getEmail(), schedule.getCreatedAt(), schedule.getModifiedAt());
    }

    // 작성자가 작성한 글을 수정일 기준 내림차순 정렬해서 반환
    // join 활용하도록 수정
    @Override
    public List<ScheduleResponseDto> findAll(String author) {
        //return jdbcTemplate.query("SELECT * FROM schedule WHERE author = ? ORDER BY modified_at DESC",
        //        scheduleRowMapper2(), author.trim());

        String sql = """
                SELECT s.id, s.user_id, s.todo, u.email AS author_email, s.created_at, s.modified_at
                FROM schedule s 
                JOIN user u ON s.user_id = u.id
                WHERE u.email = ? 
                ORDER BY s.modified_at DESC    
                """;
        return jdbcTemplate.query(sql, scheduleRowMapper2(), author.trim());
    }

    @Override
    public Schedule findByIdElseThrow(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findAny().orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found id : " + id));
    }

    @Override
    public int update(Long id, String todo, String author, String password) {
        String sql = "UPDATE schedule SET todo = ?, author = ?, modified_at = NOW() WHERE id = ? AND password = ?";
        return jdbcTemplate.update(sql, todo, author.trim(), id, password);
    }

    @Override
    public int delete(Long id, String password) {
        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ? AND password = ?", id, password);
    }
    private RowMapper<Schedule> scheduleRowMapper(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("todo"),
                        rs.getString("author"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper2() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("todo"),
                        rs.getString("author_email"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }
}
