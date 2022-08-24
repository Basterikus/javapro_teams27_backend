package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostAuthorRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.springframework.stereotype.Service;

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
                .city(cityService.findById(person.getCityId()).getTitle())
                .country(countryService.findById(
                                cityService.findById(person.getCityId()).getCountryId())
                        .getTitle())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .regDate(person.getRegDate())
                .birthDate(person.getBirthDate())
                .messagePermission(person.getMessagesPermission())
                .isBlocked(person.getIsBlocked())
                .isDeleted(false)   //TODO: ???
                .build();
    }
}
