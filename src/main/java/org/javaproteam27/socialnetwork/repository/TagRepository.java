package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {
    private final JdbcTemplate jdbcTemplate;
    public int addTag(String tagString, int postId) {
        try {
            Integer idTag = jdbcTemplate.queryForObject("SELECT MAX(id) FROM tag", Integer.class);
            idTag = (idTag != null) ? ++idTag : 0;
            if (jdbcTemplate.update("INSERT INTO tag (id, tag) " + "VALUES (?, ?)", idTag, tagString) > 0) {
                Integer idPost2tag = jdbcTemplate.queryForObject("SELECT MAX(id) FROM post2tag", Integer.class);
                idPost2tag = (idPost2tag != null) ? ++idPost2tag : 0;
                jdbcTemplate.update("INSERT INTO post2tag (id, tag_id, post_id) " + "VALUES (?, ?, ?)",
                        idPost2tag, idTag, postId);
            }
            return idTag;
        } catch (DataAccessException exception){
            return -1;
        }
    }

    private List<Integer> getTagIdsByPostId(int postId) throws DataAccessException {
        return jdbcTemplate.query("SELECT tag_id FROM post2tag WHERE post_id = " + postId,
                (rs, rowNum) -> rs.getInt("tag_id"));
    }

    public List<String> findTagsByPostId(int postId) {
        try {
            List<Integer> tagIds = getTagIdsByPostId(postId);
            ArrayList<String> tags = new ArrayList<>();
            tagIds.forEach(tagId ->
                    tags.add(jdbcTemplate.queryForObject("SELECT tag FROM tag WHERE id = " + tagId, String.class)));
            return tags;
        } catch (DataAccessException exception){
            return new ArrayList<>();
        }
    }

    public boolean deleteTagsByPostId(int postId) {
        try {
            List<Integer> tagIds = getTagIdsByPostId(postId);
            tagIds.forEach(tagId -> {
                if (jdbcTemplate.update("DELETE FROM post2tag WHERE tag_id = ?", tagId) == 1){
                    jdbcTemplate.update("DELETE FROM tag WHERE id = ?", tagId);
                }
            });
        } catch (DataAccessException exception){
            return false;
        }
        return true;
    }

    public boolean updateTagsPostId(int postId, ArrayList<String> tags) {
        if (deleteTagsByPostId(postId)) {
            tags.forEach(tag -> addTag(tag, postId));
            return true;
        }
        return false;
    }
}
