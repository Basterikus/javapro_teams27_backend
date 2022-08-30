package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.mapper.PostMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addPost(long time, int authorId, String title, String postText) {
        Integer postId = null;
        try {
            postId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post", Integer.class);
            postId = (postId != null) ? ++postId : 0;
            jdbcTemplate.update("INSERT INTO post (id, time, author_id, title, post_text) " +
                            "VALUES (?, ?, ?, ?, ?)", postId, new Timestamp(time), authorId, title, postText);
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return postId;
    }

    public List<Post> findAllUserPosts(int authorId) {
        List<Post> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post WHERE author_id = " + authorId,
                    new PostMapper());
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }

    public boolean deletePostById(int postId) {
        Boolean retValue = null;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post WHERE id = ?", postId) == 1);
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    public boolean updatePostById(int postId, String title, String postText) {
        Boolean retValue = null;
        try {
            retValue = (jdbcTemplate.update("UPDATE post SET title = ?, post_text = ? WHERE id = ?", title,
                    postText, postId) == 1);
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    public Post findPostById(int postId) {
        Post post = null;
        try {
            post = jdbcTemplate.queryForObject("SELECT * FROM post WHERE id = ?"
                    , new Object[]{postId}, new PostMapper());
        }
        catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return post;
    }
    public List<Post> findAllPublishedPosts(){
        List<Post> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post WHERE time <= CURRENT_TIMESTAMP",new PostMapper());
            //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }
}
