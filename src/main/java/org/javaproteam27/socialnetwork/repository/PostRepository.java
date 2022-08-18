package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final class PostMapper implements RowMapper<Post> {
        @Override
        public Post mapRow(ResultSet resultSet, int i) throws SQLException {
            return Post.builder()
                    .id(resultSet.getInt("id"))
                    .time(resultSet.getTimestamp("time").getTime())
                    .authorId(resultSet.getInt("author_id"))
                    .title(resultSet.getString("title"))
                    .postText(resultSet.getString("post_text"))
                    .isBlocked(resultSet.getBoolean("is_blocked"))
                    .build();
        }
    }
    public List<Post> findAllPosts(){
        return jdbcTemplate.query("SELECT * FROM post",new PostMapper());
        //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
    }
}
