package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonSettingsRs {
    private Integer id;
    @JsonProperty("person_id")
    private Integer personId;
    @JsonProperty("post_comment_notification")
    private Boolean postCommentNotification;
    @JsonProperty("comment_comment_notification")
    private Boolean commentCommentNotification;
    @JsonProperty("friend_request_notification")
    private Boolean friendRequestNotification;
    @JsonProperty("message_notification")
    private Boolean messageNotification;
    @JsonProperty("friend_birthday_notification")
    private Boolean friendBirthdayNotification;
    @JsonProperty("like_notification")
    private Boolean likeNotification;
    @JsonProperty("post_notification")
    private Boolean postNotification;
}
