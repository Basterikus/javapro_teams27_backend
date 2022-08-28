package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;

    /*public void addComment(int postId, String commentText, Integer parentId){
        try {
            Integer idComment = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post_comment WHERE id IS NOT NULL", Integer.class);
            idComment = (idComment != null) ? ++idComment : 0;
            if (jdbcTemplate.update("INSERT INTO post_comment (id, tag) " + "VALUES (?, ?)", idTag, tagString) > 0) {
                Integer idPost2tag = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post2tag WHERE id IS NOT NULL", Integer.class);
                idPost2tag = (idPost2tag != null) ? ++idPost2tag : 0;
                jdbcTemplate.update("INSERT INTO post2tag (id, tag_id, post_id) " + "VALUES (?, ?, ?)",
                        idPost2tag, idTag, postId);
            }
            return idTag;
        } catch (DataAccessException exception){
            return -1;
        }
    }*/

}
