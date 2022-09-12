package org.javaproteam27.socialnetwork.model.entity;

import lombok.Builder;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.ReadStatus;

@Data
@Builder
public class Message implements Comparable<Message> {
    
    private Integer id;
    private Long time;
    private Integer authorId;
    private Integer recipientId;
    private String messageText;
    private ReadStatus readStatus;
    private Integer dialogId;
    
    
    @Override
    public int compareTo(Message o) {
        return time.compareTo(o.time);
    }
}
