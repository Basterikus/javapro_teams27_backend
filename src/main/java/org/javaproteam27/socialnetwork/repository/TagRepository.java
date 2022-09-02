package org.javaproteam27.socialnetwork.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TagRepository {
    private final JdbcTemplate jdbcTemplate;
    public Integer addTag(String tagString, int postId) {
        Integer retValue = null;
        try {
            /*Integer tagId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM tag", Integer.class);
            tagId = (tagId != null) ? ++tagId : 0;*/
            Integer tagId;
            List<Integer> idTags = jdbcTemplate.query("SELECT id FROM tag WHERE tag = '" + tagString + "'",
                    (rs, rowNum) -> rs.getInt("id"));
            if (idTags.isEmpty()) {
                jdbcTemplate.update("INSERT INTO tag (tag) " + "VALUES (?)", tagString);
                //TODO GET ID from INSERT!
                tagId = jdbcTemplate.queryForObject("SELECT id FROM tag WHERE tag = '" + tagString + "'",
                        Integer.class);
            } else {
                tagId = idTags.stream().findFirst().get();
            }
            jdbcTemplate.update("INSERT INTO post2tag (tag_id, post_id) " + "VALUES (?, ?)", tagId, postId);
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    private List<Integer> getTagIdsByPostId(int postId) throws DataAccessException {
        return jdbcTemplate.query("SELECT tag_id FROM post2tag WHERE post_id = " + postId,
                (rs, rowNum) -> rs.getInt("tag_id"));
    }

    public List<String> findTagsByPostId(int postId) {
        List<String> retList = null;
        try {
            List<Integer> tagIds = getTagIdsByPostId(postId);
            ArrayList<String> tags = new ArrayList<>();
            tagIds.forEach(tagId -> tags.add(jdbcTemplate.queryForObject("SELECT tag FROM tag WHERE id = " + tagId,
                    String.class)));
            retList = tags;
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retList;
    }

    public Boolean deleteTagsByPostId(int postId) {
        Boolean retValue = null;
        try {
            List<Integer> tagIds = getTagIdsByPostId(postId);
            if (tagIds.isEmpty()) {
                retValue = true;
            }
            tagIds.forEach(tagId -> {
                jdbcTemplate.update("DELETE FROM post2tag WHERE tag_id = ?", tagId);
                jdbcTemplate.update("DELETE FROM tag WHERE id = ?", tagId);
            });
            retValue = true;
        } catch (DataAccessException exception){
            log.error(exception.getLocalizedMessage());
        }
        return retValue;
    }

    public Boolean updateTagsPostId(int postId, ArrayList<String> tags) {
        Boolean retValue = null;
        if (deleteTagsByPostId(postId)) {
            tags.forEach(tag -> addTag(tag, postId));
            retValue = true;
        }
        return retValue;
    }
}
