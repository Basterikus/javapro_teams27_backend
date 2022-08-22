package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationBaseDto {
    
    private Integer id;
    @JsonProperty("type_id")
    private Integer typeId;
    @JsonProperty("sent_time")
    private Long sentTime;
    @JsonProperty("entity_id")
    private Integer entityId;
    private String info;
    
}
