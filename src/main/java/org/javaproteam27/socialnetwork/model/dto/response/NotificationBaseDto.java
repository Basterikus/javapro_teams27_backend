package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;

@Data
public class NotificationBaseDto {
    
    private Integer id;
    private Integer typeId;
    private Long sentTime;
    private Integer entityId;
    private String info;
    
}