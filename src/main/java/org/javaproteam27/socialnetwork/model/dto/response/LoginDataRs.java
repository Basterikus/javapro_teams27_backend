package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

@Value
public class LoginDataRs {
    Integer id;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    @JsonProperty("reg_date")
    LocalDateTime regDate;
    @JsonProperty("birth_date")
    LocalDateTime birthDate;
    String email;
    String phone;
    String photo;
    String about;
    CityDto cityRs;
    CountryDto countryRs;
    @JsonProperty("messages_permission")
    MessagesPermission messagesPermission;
    @JsonProperty("last_online_time")
    LocalDateTime lastOnlineTime;
    boolean isBlocked;
    String token;
}
