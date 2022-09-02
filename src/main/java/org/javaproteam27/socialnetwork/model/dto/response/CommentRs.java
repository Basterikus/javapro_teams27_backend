package org.javaproteam27.socialnetwork.model.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@JsonInclude(NON_NULL)
public class CommentRs {
    private Integer id;
    private Long time;
    @JsonProperty("post_id")
    private Integer postId;
    @JsonProperty("parent_id")
    private Integer parentId;
    @JsonProperty("author")
    private PersonRs author;
    @JsonProperty("comment_text")
    private String commentText;
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
    @JsonProperty("sub_comments")
    List<CommentRs> subComments;
}


/*
        "is_deleted": false,
        "parent_id": 1,
        "comment_text": "Согласен, книга супер!",
        "id": 111,
        "post_id": "string",
        "time": 1559751301818,
        "author": {
          "id": 11,
          "first_name": "Алексеев",
          "last_name": "Александр",
          "photo": "https://cdn.dribbble.com/userupload/3396434/file/original-bb0363985b8dc8bac7b37fc2493cedfe.png?compress=1&resize=1600x1200"
        },
        "is_blocked": true,
        "sub_comments": [
          {
            "is_deleted": false,
            "parent_id": 1,
            "comment_text": "А я читал другую",
            "id": 111,
            "post_id": "string",
            "time": 1559751301818,
            "author": {
              "id": 12,
              "first_name": "Анастасия",
              "last_name": "Прокофьева",
              "photo": "https://cdn.dribbble.com/userupload/3396434/file/original-bb0363985b8dc8bac7b37fc2493cedfe.png?compress=1&resize=1600x1200"
            },
            "is_blocked": true
          }
        ]*/