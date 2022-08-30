package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;

import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.PersonRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final CityService cityService;
    private final CountryService countryService;


    public Person findById(int id) {
        return personRepository.findById(id);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Person getAuthorizedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        Person person = personRepository.findByEmail(jwtUser.getUsername());
        return person;
    }
    public ListResponseRs<PersonRs> findPerson(String firstName, String lastName, Integer ageFrom, Integer ageTo, Integer cityId, int offset, int itemPerPage) {

        return getResultJson(personRepository.findPeople(firstName, lastName, ageFrom, ageTo, cityId), offset, itemPerPage);
    }

    private ListResponseRs<PersonRs> getResultJson(List<Person> people, int offset, int itemPerPage) {

        List<PersonRs> data = people.stream()
                .map(person -> PersonRs.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .photo(person.getPhoto())
                        .birthDate(person.getBirthDate())
                        .about(person.getAbout())
                        .phone(person.getPhone())
                        .lastOnlineTime(person.getLastOnlineTime())
                        .build())
                .collect(Collectors.toList());

        return new ListResponseRs<>("", offset, itemPerPage, data);
    }

    public PersonRs initialize(Integer personId){

        Person person = findById(personId);
        return PersonRs.builder()
                .id(person.getId())
                .email(person.getEmail())
                .phone(person.getPhone())
                .city(cityService.findById(person.getCityId()).getTitle())
                .country(countryService.findById(cityService.findById(person.getCityId()).getCountryId()).getTitle())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .regDate(person.getRegDate())
                .birthDate(person.getBirthDate())
                .messagesPermission(person.getMessagesPermission())
                .isBlocked(person.getIsBlocked())
                .build();
    }
}
