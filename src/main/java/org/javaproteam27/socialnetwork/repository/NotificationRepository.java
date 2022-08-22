package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.NotificationMapper;
import org.javaproteam27.socialnetwork.model.entity.Notification;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {
    
    private final RowMapper<Notification> rowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    
    public Notification findById(int id) {
        try {
            String sql = "select * from notification where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("notification id = " + id);
        }
    }
    
    public List<Notification> findByPersonId(int personId) {
        try {
            String sql = "select * from notification where person_id = ?";
            return jdbcTemplate.query(sql, rowMapper, personId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("notification for person_id = " + personId);
        }
    }
    
}
