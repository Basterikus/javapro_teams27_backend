package org.javaproteam27.socialnetwork.model.entity;


import lombok.Data;

/*post_comment - комментарий к посту

        id
        time
        post_id
        parent_id - родительский комментарий (если ответ на комментарий к посту)
        author_id
        comment_text
        is_blocked - комментарий заблокирован*/
@Data
public class PostComment {
    private Integer id;
    private Long time;
    private String postId;
    private Integer parentId;
    private String authorId;
    private String commentText;
    private Boolean isBlocked;
}
