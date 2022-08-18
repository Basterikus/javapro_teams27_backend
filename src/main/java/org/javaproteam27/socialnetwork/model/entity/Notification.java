package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {
    
    private Integer id;
    private Integer typeId;
    private LocalDateTime sentTime;
    private Integer personId;
    private Integer entityId;
    private String contact;
    
}
