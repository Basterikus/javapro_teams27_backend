package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.mapper.PostMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;

    public int addPost(long time, int authorId, String title, String postText) {
        //insert into post (id, time, author_id, title, post_text, is_blocked)
        // values (2, TIMESTAMP '2022-04-07 21:45:38', 2, 'ivan title example', 'ivan post text example', false);
//        Integer updateReturnValue = 0;
        try {
            int id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post", Integer.class) + 1;
            jdbcTemplate.update("INSERT INTO post (id, time, author_id, title, post_text) " +
                    "VALUES (?, ?, ?, ?, ?)", id, new Timestamp(time), authorId, title, postText);
            return id;
        } catch (DataAccessException exception) {
            //throw new PostNotAddedException(exception.getMessage());
            return -1;
        }
    }

    public List<Post> findAllUserPosts(int authorId) {
        try {
            return jdbcTemplate.query("SELECT * FROM post WHERE author_id = " + authorId,
                    new PostMapper());
        } catch (DataAccessException exception) {
            return new ArrayList<>();
        }
    }

    public boolean deletePostById(int postId) {
        return jdbcTemplate.update("DELETE FROM post WHERE id = ?", postId) == 1;
    }

    public boolean updatePostById(int postId, String title, String postText) {
        return jdbcTemplate.update("UPDATE post SET title = ?, post_text = ? WHERE id = ?", title,
                postText, postId) == 1;
    }

    public Post findPostById(int postId) {
        Post post;
        try {
            post = jdbcTemplate.queryForObject("SELECT * FROM post WHERE id = ?"
                    , new Object[]{postId}, new PostMapper());
        } catch (DataAccessException exception) {
            return null;
        }
        return post;
    }

    public List<Post> findAllPublishedPosts() {
        try {
            return jdbcTemplate.query("SELECT * FROM post WHERE time <= CURRENT_TIMESTAMP", new PostMapper());
            //                "SELECT * FROM post WHERE post_text LIKE '%" + postText + "%'"
        } catch (DataAccessException exception) {
            //TODO: add logging;
            return new ArrayList<>();
        }
    }

    public List<Post> findPost(String text, Long dateFrom, Long dateTo) {
        ArrayList<String> queryParts = new ArrayList<>();

        if (text != null) {
            queryParts.add("post_text LIKE '%" + text + "%'");
        }

        if (dateFrom != null) {
            LocalDate dateFromParsed =
                    Instant.ofEpochMilli(dateFrom).atZone(ZoneId.systemDefault()).toLocalDate();
            queryParts.add("time > '" + dateFromParsed + "'::date");
        }

        if (dateTo != null) {
            LocalDate dateToParsed =
                    Instant.ofEpochMilli(dateTo).atZone(ZoneId.systemDefault()).toLocalDate();
            queryParts.add("time < '" + dateToParsed + "'::date");
        }

        String buildQuery = "SELECT * FROM post WHERE " + String.join(" AND ", queryParts) + ";";
        return jdbcTemplate.query(buildQuery, new PostMapper());
    }
}
