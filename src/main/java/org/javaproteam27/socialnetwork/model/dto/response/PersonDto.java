package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.javaproteam27.socialnetwork.model.entity.Country;
import org.javaproteam27.socialnetwork.model.entity.Person;
@Data
@NoArgsConstructor
public class PersonDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Long regDate;
    private Long birthDate;
    private String email;
    private String phone;
    private String photo;
    private String about;
    private City city;
    private Country country;
    private Person.MessagesPermissionType messagesPermission;
    private Long lastOnlineTime;
    private Boolean isBlocked;
    private String token;


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
        city = person.getCity();
        country = person.getCountry();
        messagesPermission = person.getMessagesPermission();
        lastOnlineTime = person.getLastOnlineTime();
        isBlocked = person.getIsBlocked();
        token=null;
    }

    public void setExampleToLogin(){
        id = 1;
        firstName = "Petr";
        lastName = "Ivanov";
        regDate = 1559751301818L;
        birthDate = 1559751301818L;
        email = "petr@mail.ru";
        phone = "89100000000";
        photo = "https://...../photos/image123.jpg";
        about = "Родился в небольшой, но честной семье";
        city = new City(1, "Москва");
        country = new Country(1, "Россия");
        messagesPermission = Person.MessagesPermissionType.All;
        lastOnlineTime = 1559751301818L;
        isBlocked = false;
        token = "1q2e3e3r4t5";
    }
}
