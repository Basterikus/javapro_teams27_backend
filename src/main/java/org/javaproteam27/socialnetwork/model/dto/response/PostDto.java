package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;
import org.javaproteam27.socialnetwork.model.entity.PostComment;

import java.util.List;
        /*"id": 1,
        "time": 1559751301818,
        PostAuthorDto author;
        "title": "string",
        "post_text": "string",
        "is_blocked": false,
        "likes": 23,
        "comments":     [ PostComment postComment;]*/
@Data
public class PostDto {
    private Integer id;
    private Long time;
    private PersonDto author;
    private String title;
    private Boolean isBlocked;
    private Integer likes;
    private List<PostComment> postComments;
}
