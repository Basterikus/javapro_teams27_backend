package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {
    private final JdbcTemplate jdbcTemplate;
    public Integer countByPostId(Integer postId){
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM post_like WHERE post_id = " + postId, Integer.class);
    }
}
