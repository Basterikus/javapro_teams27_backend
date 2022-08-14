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
    public List<Post> findPostsByPostText(){
        return jdbcTemplate.queryForList(
                "SELECT * FROM post"
//                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
                , Post.class);
    }
}
