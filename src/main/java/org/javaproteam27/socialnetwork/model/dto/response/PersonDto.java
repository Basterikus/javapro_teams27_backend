package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.javaproteam27.socialnetwork.model.entity.Person;

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
    private String messagesPermission; // enum
    private LocalDateTime lastOnlineTime;
    private boolean isBlocked;
    
    
//    private PersonDto(Person person) {
//
//        this.id = person.getId();
//        this.firstName = person.getFirstName();
//        this.lastName = person.getLastName();
//        this.regDate = person.getRegDate();
//        this.birthDate = person.getBirthDate();
//        this.email = person.getEmail();
//        this.phone = person.getPhone();
//        this.photo = person.getPhoto();
//        this.about = person.getAbout();
//        this.cityDto = person.getCity();
//        this.countryDto = person.getCountry();
//        this.messagesPermission = person.getMessagesPermission();
//        this.lastOnlineTime = person.getLastOnlineTime();
//        this.isBlocked = person.isBlocked();
//
//    }
    
//    private PersonDto getPersonDto(Person person) {
//        return new PersonDtoBuilder()
//                .id(person.getId())
//                .firstName(person.getFirstName())
//                .lastName(person.getLastName())
//                .regDate(person.getRegDate())
//                .birthDate(person.getBirthDate())
//                .email(person.getEmail())
//                .phone(person.getPhone())
//                .photo(person.getPhoto())
//                .about(person.getAbout())
//                .cityDto(person.getCity())
//                .countryDto(person.getCountry())
//                .messagesPermission(person.getMessagesPermission())
//                .lastOnlineTime(person.getLastOnlineTime())
//                .isBlocked(person.isBlocked())
//                .build();
//    }
    
}
