package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRq {
    @JsonProperty("parent_id")
    Integer parentId;
    @JsonProperty("comment_text")
    String commentText;
}