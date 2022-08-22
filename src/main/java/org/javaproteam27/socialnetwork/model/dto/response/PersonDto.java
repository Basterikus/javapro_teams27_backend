package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    String token;

    public PersonDto(Person person){
        id = person.getId();
        firstName = person.getFirstName();
        lastName = person.getLastName();
        regDate = person.getRegDate();
        birthDate = person.getBirthDate();
        email = person.getEmail();
        phone = person.getPhone();
        photo = person.getPhoto();
        about = person.getAbout();
        city = new CityDto(person.getCity());
        country = new CountryDto(person.getCountry());
        messagesPermission = person.getMessagesPermission();
        lastOnlineTime = person.getLastOnlineTime();
        isBlocked = person.getIsBlocked();
        token=null;
    }

    public void setExampleToLogin(String token){
        id = 1;
        firstName = "Petr";
        lastName = "Ivanov";
        /*LocalDateTime localDateTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(1559751301818L),
                        TimeZone.getDefault().toZoneId());*/
        LocalDateTime localDateTime = LocalDateTime.now();
        regDate = localDateTime;
        birthDate = localDateTime;
        email = "petr@mail.ru";
        phone = "89100000000";
        photo = "https://...../photos/image123.jpg";
        about = "Родился в небольшой, но честной семье";
        city = new CityDto(1, "Москва");
        country = new CountryDto(1, "Россия");
        messagesPermission = MessagesPermission.ALL;
        lastOnlineTime = localDateTime;
        isBlocked = false;
        this.token = token;
    }
}

