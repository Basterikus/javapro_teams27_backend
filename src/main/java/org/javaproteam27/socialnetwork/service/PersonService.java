package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.CityDto;
import org.javaproteam27.socialnetwork.model.dto.response.CountryDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.javaproteam27.socialnetwork.model.entity.Country;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public ListResponseDto<PersonDto> findPerson(String firstName, String lastName, Integer ageFrom, Integer ageTo, Integer cityId, int offset, int itemPerPage) {

        return getResultJson(personRepository.findPeople(firstName, lastName, ageFrom, ageTo, cityId), offset, itemPerPage);
    }

    private ListResponseDto<PersonDto> getResultJson(List<Person> people, int offset, int itemPerPage) {

        List<PersonDto> data = people.stream()
                .map(person -> PersonDto.builder()
                        .firstName(person.getFirstName())
                        .lastName(person.getLastName())
                        .photo(person.getPhoto())
                        .birthDate(person.getBirthDate())
                        .about(person.getAbout())
                        .phone(person.getPhone())
                        .lastOnlineTime(person.getLastOnlineTime())
                        .build())
                .collect(Collectors.toList());

        return new ListResponseDto<>("", offset, itemPerPage, data);
    }
}
