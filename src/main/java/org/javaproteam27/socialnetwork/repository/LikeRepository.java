package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.ErrorException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    public void addPostLike(long time, Integer personId, Integer postId){
        try {
            jdbcTemplate.update("INSERT INTO post_like (time, person_id, post_id) " + "VALUES (?, ?, ?)",
                    new Timestamp(time), personId, postId);
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
    }

    public Boolean deletePostLike(Integer postId, Integer userId){
        Boolean retValue;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post_like WHERE post_id = " + postId +
                    " AND person_id = " + userId) == 1);
        } catch (DataAccessException exception) {
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }

    public List<Integer> getLikesByPersonId(Integer personId, Integer postId){
        List<Integer> retList;
        try {
            retList = jdbcTemplate.query("SELECT id FROM post_like WHERE person_id = "
                            + personId + " AND post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("id"));
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }

    public List<Integer> getLikesByPostId(Integer postId){

        List<Integer> retList;
        try {
            retList = jdbcTemplate.query("SELECT id FROM post_like WHERE post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("id"));
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }

    public List<Integer> getUserListLikedPost(Integer postId){
        List<Integer> retList;
        try {
            retList = jdbcTemplate.query("SELECT person_id FROM post_like WHERE post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("person_id"));
        } catch (DataAccessException exception) {
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }
}
