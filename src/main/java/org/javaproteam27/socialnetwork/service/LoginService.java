package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.InvalidRequestException;
import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.*;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.javaproteam27.socialnetwork.model.entity.Country;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.CityRepository;
import org.javaproteam27.socialnetwork.repository.CountryRepository;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PersonRepository personRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public LoginRs login(LoginRq loginRq) {
        String email = loginRq.getEmail();
        String password = loginRq.getPassword();
        Person person = personRepository.findByEmail(email);
        if (person.getPassword().contains(password)) {
            String token = getToken(email);
            City city = cityRepository.findById(person.getCityId());
            Country country = countryRepository.findById(city.getCountryId());
            return new LoginRs("", new Date(), new LoginDataRs(person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getRegDate(),
                    person.getBirthDate(),
                    person.getEmail(),
                    person.getPhone(),
                    person.getPhoto(),
                    person.getAbout(),
                    new CityDto(city.getId(), city.getTitle()),
                    new CountryDto(country.getId(), country.getTitle()),
                    person.getMessagesPermission(),
                    person.getLastOnlineTime(),
                    person.getIsBlocked(),
                    token));
        } else throw new InvalidRequestException("Incorrect password");
    }

    public LogoutRs logout() {
        return new LogoutRs("", new Date(), new LogoutDataRs("ok"));
    }

    public PersonDto profileResponse(String token) {
        String email = jwtTokenProvider.getUsername(token);
        Person person = personRepository.findByEmail(email);
        City city = cityRepository.findById(person.getCityId());
        Country country = countryRepository.findById(city.getCountryId());
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
                .city(new CityDto(city))
                .country(new CountryDto(country))
                .messagesPermission(person.getMessagesPermission())
                .lastOnlineTime(person.getLastOnlineTime())
                .isBlocked(person.getIsBlocked())
                .token(token).build();
    }


    private String getToken(String email) {
        return jwtTokenProvider.createToken(email);
    }
}
