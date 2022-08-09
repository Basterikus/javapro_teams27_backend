package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.EntityNotFoundException;
import org.javaproteam27.socialnetwork.model.entity.Friendship;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.FriendshipStatusCode;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipRepository {
    
    private final RowMapper<Friendship> rowMapper = (rs, rowNum) -> {
    
        Friendship friendship = new Friendship();
        
        friendship.setId(rs.getInt("id"));
        friendship.setStatusId(rs.getInt("status_id"));
        friendship.setSentTime(rs.getTimestamp("sent_time").toLocalDateTime());
        friendship.setSrcPersonId(rs.getInt("src_person_id"));
        friendship.setDstPersonId(rs.getInt("dst_person_id"));
        
        return friendship;
    };
    
    private final JdbcTemplate jdbcTemplate;
    
    
    public void save(Friendship friendship) {
        String sql = "insert into friendship(status_id, sent_time, src_person_id, dst_person_id) " +
                "values (?,?,?,?)";
        jdbcTemplate.update(sql, friendship.getStatusId(), friendship.getSentTime(),
                friendship.getSrcPersonId(), friendship.getDstPersonId());
    }
    
    public List<Friendship> findByPersonId(int id) {
        try {
            String sql = "select * from friendship where src_person_id = ? or dst_person_id = ?";
            return jdbcTemplate.query(sql, rowMapper, id, id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("person_id = " + id);
        }
    }
    
    public List<Friendship> findByPersonIdAndStatus(Integer id, FriendshipStatusCode statusCode) {
        try {
            String sql = "select * from friendship fs " +
                    "join friendship_status fss on fs.status_id = fss.id " +
                    "where (src_person_id = ? or dst_person_id = ?) and code like ?";
            return jdbcTemplate.query(sql, rowMapper, id, id, statusCode.toString());
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("person_id = " + id);
        }
    }
    
}
