package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.PostComment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCommentRepository {
    private final JdbcTemplate jdbcTemplate;
    public List<PostComment> getPostCommentsByPostId(Integer postId){
        return jdbcTemplate.queryForList(
                "SELECT * FROM post_comment WHERE post_id = " + postId, PostComment.class);
    }
}
