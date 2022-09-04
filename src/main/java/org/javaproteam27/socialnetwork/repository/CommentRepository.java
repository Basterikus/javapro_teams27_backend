package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.ErrorException;
import org.javaproteam27.socialnetwork.mapper.CommentMapper;
import org.javaproteam27.socialnetwork.model.entity.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addComment(int postId, String commentText, Integer parentId, Integer authorId, Long time){

        Integer retValue;
        try {
            String sqlQuery = "INSERT INTO post_comment (time, post_id, parent_id, author_id, comment_text, is_blocked)"
                    + " VALUES ('" + new Timestamp(time) + "', " + postId + ", " + parentId + ", " + authorId
                    + ", '" + commentText + "', " + false + ")";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> connection.prepareStatement(sqlQuery, new String[]{"id"}), keyHolder);
            retValue = (Integer) keyHolder.getKey();
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }

    public List<Comment> getAllCommentsByPostId(int postId) {

        List<Comment> retList;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post_comment WHERE post_id = " + postId, new CommentMapper());
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }

    public List<Comment> getAllCommentsByPostIdAndParentId(Integer postId, Integer parentId) {

        List<Comment> retList;
        try {
            String connector = (parentId == null) ? "is " : "= ";
            retList = jdbcTemplate.query("SELECT * FROM post_comment WHERE post_id = " + postId
                    + " AND parent_id " + connector + parentId, new CommentMapper());
        } catch (DataAccessException exception){
            throw new ErrorException(exception.getMessage());
        }
        return retList;
    }

    public Boolean deleteComment(int postId, int commentId) {

        Boolean retValue;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post_comment WHERE id = " + commentId +
                    " AND post_id = " + postId) == 1);
        } catch (DataAccessException exception) {
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }

    public Boolean editComment(int postId, int commentId, String commentText, Long time) {
        Boolean retValue;
        try {
            retValue = (jdbcTemplate.update("UPDATE post_comment SET comment_text = ?, time = ? " +
                            "WHERE id = ? AND post_id = ?", commentText, new Timestamp(time), commentId, postId) == 1);
        } catch (DataAccessException exception) {
            throw new ErrorException(exception.getMessage());
        }
        return retValue;
    }
}
