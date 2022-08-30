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


    public Person findById(int id) {
        return personRepository.findById(id);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public ListResponseRs<PersonRs> findPerson(String firstName, String lastName, Integer ageFrom, Integer ageTo,
                                               String city, String country, int offset, int itemPerPage) {

        Person authorizedPerson = getAuthorizedPerson();
        return getResultJson(personRepository.findPeople(authorizedPerson, firstName, lastName, ageFrom, ageTo, city,
                        country),
                offset, itemPerPage);
    }

    public Person getAuthorizedPerson() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        return personRepository.findByEmail(jwtUser.getUsername());
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
                        .country(person.getCountry())
                        .city(person.getCity())
                        .build())
                .collect(Collectors.toList());

        return new ListResponseRs<>("", offset, itemPerPage, data);
    }
}
