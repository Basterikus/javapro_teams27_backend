package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javaproteam27.socialnetwork.model.enums.ReadStatus;

import java.time.LocalDateTime;

@Data
public class Message {
    
    private Integer id;
    private LocalDateTime time;
    private Integer authorId;
    private Integer recipientId;
    private String messageText;
    private ReadStatus readStatus;
    
}
