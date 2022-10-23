package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.NotificationType;

@Data
@Builder
public class PersonSettingsRs {
    @JsonProperty("notification_type")
    private NotificationType notificationType;
    @JsonProperty("enable")
    private Boolean enable;
}
