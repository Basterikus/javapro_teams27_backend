package org.javaproteam27.socialnetwork.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

/*"author": {
        "id": 2,
        "email": "ivanov@mail.ru",
        "phone": "+7(999)9999999",
        "city": "Tver",
        "country": "Russia",
        "first_name": "Ivan",
        "last_name": "Ivanov",
        "reg_date": 1649367846500,
        "birth_date": 1649367846000,
        "messages_permission": "ALL",
        "is_blocked": false,
        "is_deleted": false
        }*/
@Data
@Builder
//@JsonRootName("author")
@JsonPropertyOrder({"id", "email", "phone", "city", "country", "first_name", "last_name",
        "reg_date", "birth_date", "messages_permission", "is_blocked", "is_deleted"})
public class PostAuthorDtoRs {
    private Integer id;
    private String email;
    private String phone;
    private String city;
    private String country;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("reg_date")
    private LocalDateTime regDate;
    @JsonProperty("birth_date")
    private LocalDateTime birthDate;
    @JsonProperty("messages_permission")
    private MessagesPermission messagePermission;
    /*@JsonProperty("last_online_time")
    private LocalDateTime lastOnlineTime;
    private String info;*/
    @JsonProperty("is_blocked")
    private Boolean isBlocked;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
}
