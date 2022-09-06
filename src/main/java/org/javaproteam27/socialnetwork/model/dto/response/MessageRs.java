package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.ReadStatus;

import java.time.LocalDateTime;

@Data
public class MessageRs {
    
    private Integer id;
    private LocalDateTime time;
    @JsonProperty("author_id")
    private Integer authorId;
    @JsonProperty("recipient_id")
    private Integer recipientId;
    @JsonProperty("message_text")
    private String messageText;
    @JsonProperty("read_status")
    private ReadStatus readStatus;
    
}
