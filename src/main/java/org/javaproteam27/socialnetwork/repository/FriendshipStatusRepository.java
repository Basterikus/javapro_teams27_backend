package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.FriendshipStatusMapper;
import org.javaproteam27.socialnetwork.model.entity.FriendshipStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendshipStatusRepository {
    
    private final RowMapper<FriendshipStatus> rowMapper = new FriendshipStatusMapper();
    private final JdbcTemplate jdbcTemplate;
    
    
    public FriendshipStatus findById(int id) {
        try {
            String sql = "select * from friendship_status where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("friendship_status id = " + id);
        }
    }
    
}
