package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.NotificationTypeCode;

@Data
public class NotificationType {

    private Integer id;
    private NotificationTypeCode code;
    private String name;

}
