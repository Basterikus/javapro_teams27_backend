package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostAuthorDtoRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostAuthorDtoService {
    private final CityService cityService;
    private final CountryService countryService;
    public PostAuthorDtoRs initialize(Person person){
        return PostAuthorDtoRs.builder()
                .id(person.getId())
                .email(person.getEmail())
                .phone(person.getPhone())
                .city(cityService.findById(person.getCityId()).getTitle())
                .country(countryService.findById(
                                cityService.findById(person.getCityId()).getCountryId())
                        .getTitle())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .regDate(person.getRegDate()) //.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .birthDate(person.getBirthDate()) //.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .messagePermission(person.getMessagesPermission())
                .lastOnlineTime(LocalDateTime.now())
                .info("123")
                .isBlocked(person.getIsBlocked())
                .isDeleted(false)   //TODO: ???
                .build();
    }
}
