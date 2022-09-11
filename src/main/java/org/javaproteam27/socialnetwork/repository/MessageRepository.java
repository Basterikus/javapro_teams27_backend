package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.MessageMapper;
import org.javaproteam27.socialnetwork.model.entity.Message;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    
    private final MessageMapper rowMapper = new MessageMapper();
    private final JdbcTemplate jdbcTemplate;
    
    
    public Message findById(Integer id) {
        
        try {
            String sql = "select * from message where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("message id = " + id);
        }
    }
    
    public Integer getUnreadedCount(Integer recipientId) {
        
        String sql = "select count(*) from message where recipient_id = ? and read_status like 'SENT'";
        return jdbcTemplate.queryForObject(sql, Integer.class, recipientId);
    }
    
    public Integer getUnreadedCountByDialogId(Integer dialogId) {
        
        String sql = "select count(*) from message where dialog_id = ? and read_status like 'SENT'";
        return jdbcTemplate.queryForObject(sql, Integer.class, dialogId);
    }
    
}
