package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class LikeRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addPostLike(Long time, Integer personId, Integer postId){
        Integer idLike = null;
        try {
            idLike = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post_like", Integer.class);
            idLike = (idLike != null) ? ++idLike : 0;
            jdbcTemplate.update("INSERT INTO post_like " + "VALUES (?, ?, ?, ?)",
                    idLike, new Timestamp(time), personId, postId);
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return idLike;
    }

    public Boolean deletePostLike(Integer postId, Integer userId){
        Boolean retValue = null;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post_like WHERE post_id = " + postId +
                    " AND person_id = " + userId) == 1);
        } catch (DataAccessException exception) {
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    public List<Integer> getLikesByPersonId(Integer personId, Integer postId){
        List<Integer> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT id FROM post_like WHERE person_id = "
                            + personId + " AND post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("id"));
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }

    public List<Integer> getLikesByPostId(Integer postId){

        List<Integer> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT id FROM post_like WHERE post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("id"));
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }

    public List<Integer> getUserListLikedPost(Integer postId){
        List<Integer> retList = new ArrayList<>();
        try {
            retList = jdbcTemplate.query("SELECT person_id FROM post_like WHERE post_id = " + postId,
                    (rs, rowNum) -> rs.getInt("person_id"));
        } catch (DataAccessException exception) {
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }
}
