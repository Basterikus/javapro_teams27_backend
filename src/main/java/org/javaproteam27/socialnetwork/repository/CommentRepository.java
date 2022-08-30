package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.mapper.CommentMapper;
import org.javaproteam27.socialnetwork.model.dto.response.CommentRs;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer addComment(int postId, String commentText, Integer parentId, Integer authorId, Long time){

        Integer retValue = null;
        try {
            Integer idComment = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post_comment", Integer.class);
            idComment = (idComment != null) ? ++idComment : 0;
            parentId= (parentId == -1) ? idComment : parentId;
            if (jdbcTemplate.update("INSERT INTO post_comment " + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    idComment, new Timestamp(time), postId, parentId, authorId, commentText, false) > 0) {
                retValue = idComment; //(id, time, post_id, parent_id, author_id, comment_text, is_blocked)
            }
        } catch (DataAccessException exception){
        }
        return retValue;
    }

    public List<CommentRs> getAllCommentsByPostId(int postId) {

        List<CommentRs> retList = null;
        try {
            retList = jdbcTemplate.query("SELECT * FROM post_comment WHERE post_id = " + postId,
                    new CommentMapper());
        } catch (DataAccessException exception){
        }
        return retList;
    }

    public Boolean deleteComment(int postId, int commentId) {

        Boolean retValue = null;
        try {
            retValue = (jdbcTemplate.update("DELETE FROM post_comment WHERE id = " + commentId +
                    " AND post_id = " + postId) == 1);
        } catch (DataAccessException exception) {
        }
        return retValue;
    }
}
