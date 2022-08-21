package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.PostNotAddedException;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public int addPost(LocalDateTime time, int authorId, String title, String postText) throws PostNotAddedException {
        //insert into post (id, time, author_id, title, post_text, is_blocked)
        // values (2, TIMESTAMP '2022-04-07 21:45:38', 2, 'ivan title example', 'ivan post text example', false);
//        Integer updateReturnValue = 0;
        try {
            int id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post", Integer.class) + 1;
            jdbcTemplate.update("INSERT INTO post (id, time, author_id, title, post_text) " +
                            "VALUES (?, ?, ?, ?, ?)"
                    , id
                    , Timestamp.valueOf(time)
                    , authorId
                    , title
                    , postText);
            return id;
        } catch (DataAccessException exception){
            throw new PostNotAddedException(exception.getMessage());
        }
    }

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
        try {
            return jdbcTemplate.query("SELECT * FROM post",new PostMapper());
            //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
        } catch (DataAccessException exception){
            //TODO: add logging;
            return new ArrayList<>();
        }
    }
}
