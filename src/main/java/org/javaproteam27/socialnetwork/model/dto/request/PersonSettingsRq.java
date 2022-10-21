package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonSettingsRq {
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
