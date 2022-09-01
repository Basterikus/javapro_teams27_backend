package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import liquibase.pro.packaged.S;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

@Data
public class UserRq {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("birth_date")
    private long birthDate;

    private String phone;

    @JsonProperty("photo_id")
    private String photoId;

    private String about;

    @JsonProperty("town_id")
    private int townId;

    @JsonProperty("country_id")
    private int countryId;

    @JsonProperty("messages_permission")
    private String messagesPermission;
}
