package org.javaproteam27.socialnetwork.mapper;

import org.javaproteam27.socialnetwork.model.dto.response.CommentRs;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<CommentRs> {
    @Override
    public CommentRs mapRow(ResultSet resultSet, int i) throws SQLException {
        return CommentRs.builder()
                .id(resultSet.getInt("id"))
                .time(resultSet.getTimestamp("time").getTime())
                .authorId(resultSet.getInt("author_id"))
                .isBlocked(resultSet.getBoolean("is_blocked"))
                .commentText(resultSet.getString("comment_text"))
                .postId(resultSet.getInt("post_id"))
                .parentId(resultSet.getInt("parent_id"))
                .build();
    }
}
