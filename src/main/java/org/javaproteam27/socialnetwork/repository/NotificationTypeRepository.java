package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.NotificationTypeMapper;
import org.javaproteam27.socialnetwork.model.entity.NotificationType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationTypeRepository {
    
    private final RowMapper<NotificationType> rowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    
    public NotificationType findById(int id) {
        try {
            String sql = "select * from notification_type where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("notification_type id = " + id);
        }
    }
}
