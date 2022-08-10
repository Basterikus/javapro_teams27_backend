package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PersonDto {
    
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime regDate;
    private LocalDateTime birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private CityDto city;
    private CountryDto country;
    private MessagesPermission messagesPermission;
    private LocalDateTime lastOnlineTime;
    private boolean isBlocked;
    
}
