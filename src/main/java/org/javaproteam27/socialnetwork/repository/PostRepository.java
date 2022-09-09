package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.ErrorException;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.mapper.PostMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addPost(long time, int authorId, String title, String postText) {
        Integer postId;
        try {
            String sqlQuery = "INSERT INTO post (time, author_id, title, post_text) " +
                    "VALUES ('" + new Timestamp(time) + "', " + authorId + ", '" + title + "', '" + postText + "')";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> connection.prepareStatement(sqlQuery, new String[]{"id"}), keyHolder);
            postId = (Integer) keyHolder.getKey();
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return postId;
    }

    public List<Post> findAllUserPosts(int authorId, int offset, int limit) {
        List<Post> retList;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post WHERE author_id = " + authorId + " LIMIT "
                            + limit + " OFFSET  " + offset, new PostMapper());
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }

    public Boolean deletePostById(int postId) {
        Boolean retValue;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post WHERE id = ?", postId) == 1);
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }

    public Boolean updatePostById(int postId, String title, String postText) {
        Boolean retValue;
        try {
            retValue = (jdbcTemplate.update("UPDATE post SET title = ?, post_text = ? WHERE id = ?", title,
                    postText, postId) == 1);
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }

    public Post findPostById(int postId) {
        Post post;
        try {
            post = jdbcTemplate.queryForObject("SELECT * FROM post WHERE id = ?"
                    , new Object[]{postId}, new PostMapper());
        }
        catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return post;
    }
    public List<Post> findAllPublishedPosts(int offset, int limit){
        List<Post> retList;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post WHERE time <= CURRENT_TIMESTAMP LIMIT " + limit
                    + " OFFSET " + offset,new PostMapper());
            //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }

        return retList;
    }

    public List<Post> findPost(String text, Long dateFrom, Long dateTo) {
        ArrayList<String> queryParts = new ArrayList<>();

        if (text != null) {
            queryParts.add("post_text LIKE '%" + text + "%'");
        }

        if (dateFrom != null) {
            LocalDate dateFromParsed =
                    Instant.ofEpochMilli(dateFrom).atZone(ZoneId.systemDefault()).toLocalDate();
            queryParts.add("time > '" + dateFromParsed + "'::date");
        }

        if (dateTo != null) {
            LocalDate dateToParsed =
                    Instant.ofEpochMilli(dateTo).atZone(ZoneId.systemDefault()).toLocalDate();
            queryParts.add("time < '" + dateToParsed + "'::date");
        }

        String buildQuery = "SELECT * FROM post WHERE " + String.join(" AND ", queryParts) + ";";
        return jdbcTemplate.query(buildQuery, new PostMapper());
    }

}
