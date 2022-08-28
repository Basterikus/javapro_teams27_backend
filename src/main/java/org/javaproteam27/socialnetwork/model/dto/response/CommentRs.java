package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentRs {
    @JsonProperty("parent_id")
    Integer parentId;
    @JsonProperty("comment_text")
    String commentText;
    Integer id;
    @JsonProperty("post_id")
    Integer postId;
    Long time;
    @JsonProperty("author_id")
    Integer authorId;
    @JsonProperty("is_blocked")
    Boolean isBlocked;
}
