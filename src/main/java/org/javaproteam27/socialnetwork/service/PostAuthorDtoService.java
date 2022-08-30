package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostAuthorRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class PostAuthorDtoService {
    private final CityService cityService;
    private final CountryService countryService;
    public PostAuthorRs initialize(Person person){
        return PostAuthorRs.builder()
                .id(person.getId())
                .email(person.getEmail())
                .phone(person.getPhone())
                .city(cityService.findByTitle(person.getCity()).getTitle())
                .country(countryService.findById(
                                cityService.findByTitle(person.getCity()).getCountryId())
                        .getTitle())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .regDate(person.getRegDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .birthDate(person.getBirthDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .messagePermission(person.getMessagesPermission())
                .isBlocked(person.getIsBlocked())
                .isDeleted(false)   //TODO: ???
                .build();
    }
}
