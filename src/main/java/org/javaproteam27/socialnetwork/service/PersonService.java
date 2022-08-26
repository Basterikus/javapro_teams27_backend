package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;

import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.PersonRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
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
}
