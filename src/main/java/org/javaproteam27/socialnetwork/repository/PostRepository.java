package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;
    public List<Post> findAllPosts(){
        //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
        return jdbcTemplate.query("SELECT * FROM post",
                (rs, rowNum) -> Post.builder()
                        .id(rs.getInt("id"))
                        .time(rs.getTimestamp("time").getTime())
                        .authorId(rs.getInt("author_id"))
                        .title(rs.getString("title"))
                        .postText(rs.getString("post_text"))
                        .isBlocked(rs.getBoolean("is_blocked"))
                        .build());
    }
}
