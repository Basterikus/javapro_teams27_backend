package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.model.entity.FriendshipStatus;
import org.javaproteam27.socialnetwork.model.enums.FriendshipStatusCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendshipStatusRepository {
    
    private final RowMapper<FriendshipStatus> rowMapper = (rs, rowNum) -> {
    
        FriendshipStatus friendshipStatus = new FriendshipStatus();
        
        friendshipStatus.setId(rs.getInt("id"));
        friendshipStatus.setTime(rs.getTimestamp("time").toLocalDateTime());
        friendshipStatus.setName(rs.getString("name"));
        friendshipStatus.setCode(FriendshipStatusCode.valueOf(rs.getString("code")));
        
        return friendshipStatus;
    };
    
    private final JdbcTemplate jdbcTemplate;
    
    
    public FriendshipStatus findById(int id) {
        try {
            String sql = "select * from friendship_status where id = ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("city_id = " + id);
        }
    }
    
}
