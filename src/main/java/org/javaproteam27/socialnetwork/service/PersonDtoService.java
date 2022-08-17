package org.javaproteam27.socialnetwork.service;

import org.javaproteam27.socialnetwork.model.dto.response.CityDto;
import org.javaproteam27.socialnetwork.model.dto.response.CountryDto;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonDtoService {
    public PersonDto initialize(Person person){
        return PersonDto.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .regDate(person.getRegDate())
                .birthDate(person.getBirthDate())
                .email(person.getEmail())
                .phone(person.getPhone())
                .photo(person.getPhoto())
                .about(person.getAbout())
                .city(new CityDto(person.getCityId(), ""))
                .country(new CountryDto(1,""))
                .messagesPermission(person.getMessagesPermission())
                .lastOnlineTime(person.getLastOnlineTime())
                .isBlocked(person.getIsBlocked()).build();
    }
}
