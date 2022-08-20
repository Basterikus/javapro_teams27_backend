package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonPropertyOrder({"id", "first_name", "last_name", "reg_date", "birth_date", "email", "phone", "photo",
        "about", "city", "country", "messages_permission", "last_online_time", "info", "token", "blocked"})
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
    private String city;
    private String country;
    @JsonProperty("messages_permission")
    private MessagesPermission messagesPermission;
    @JsonProperty("last_online_time")
    private LocalDateTime lastOnlineTime;
    private String info;
    @JsonProperty("blocked")
    private boolean isBlocked;
    String token;
}

