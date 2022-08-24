package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PersonDto {

    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("reg_date")
    private LocalDateTime regDate;
    @JsonProperty("birth_date")
    private LocalDateTime birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private CityDto city;
    private CountryDto country;
    @JsonProperty("messages_permission")
    private MessagesPermission messagesPermission;
    @JsonProperty("last_online_time")
    private LocalDateTime lastOnlineTime;
    @JsonProperty("is_blocked")
    private boolean isBlocked;
    String token;
}

