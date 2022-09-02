package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javaproteam27.socialnetwork.mapper.CommentMapper;
import org.javaproteam27.socialnetwork.model.entity.Comment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addComment(int postId, String commentText, Integer parentId, Integer authorId, Long time){

        Integer retValue = null;
        try {
//            Integer test = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post_comment", Integer.class);
//            parentId = (parentId == null) ? 0 : parentId;
//            idComment = (idComment != null) ? ++idComment : 0;
            if (jdbcTemplate.update("INSERT INTO post_comment (time, post_id, parent_id, " +
                            "author_id, comment_text, is_blocked) " + "VALUES (?, ?, ?, ?, ?, ?)",
                    new Timestamp(time), postId, parentId, authorId, commentText, false) > 0) {
                if (parentId == null) { //TODO: GET ID FROM jdbcTemplate.update("INSERT INTO !!!
                    retValue = jdbcTemplate.queryForObject("SELECT id FROM post_comment WHERE post_id = " + postId +
                            " AND author_id = " + authorId + " AND comment_text = '" + commentText + "'", Integer.class); //(id, time, post_id, parent_id, author_id, comment_text, is_blocked)
                } else {
                    retValue = jdbcTemplate.queryForObject("SELECT id FROM post_comment WHERE post_id = " + postId +
                            " AND parent_id = " + parentId + " AND author_id = " + authorId + " AND comment_text = '" +
                            commentText + "'", Integer.class); //(id, time, post_id, parent_id, author_id, comment_text, is_blocked)
                }
            }
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    public List<Comment> getAllCommentsByPostId(int postId) {

        List<Comment> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post_comment WHERE post_id = " + postId,
                    new CommentMapper());
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }

    public Boolean deleteComment(int postId, int commentId) {

        Boolean retValue = null;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post_comment WHERE id = " + commentId +
                    " AND post_id = " + postId) == 1);
        } catch (DataAccessException exception) {
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }
}
