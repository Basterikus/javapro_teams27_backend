package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.mapper.FriendshipStatusMapper;
import org.javaproteam27.socialnetwork.model.entity.FriendshipStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;

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


    public int save(FriendshipStatus friendshipStatus) {

        String sql = "insert into friendship_status(time, name, code) " +
                "values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setTimestamp(1, Timestamp.valueOf(friendshipStatus.getTime()));
            ps.setString(2, friendshipStatus.getName());
            ps.setString(3, friendshipStatus.getCode().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public void delete(FriendshipStatus friendshipStatus) {
        String sql = "delete from friendship_status where id = " + friendshipStatus.getId();
        jdbcTemplate.update(sql);
    }


}
