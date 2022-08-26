package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.UserRq;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRs;
import org.javaproteam27.socialnetwork.model.dto.response.UserRs;
import org.javaproteam27.socialnetwork.model.entity.City;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.MessagesPermission;
import org.javaproteam27.socialnetwork.repository.CityRepository;
import org.javaproteam27.socialnetwork.repository.CountryRepository;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public ResponseEntity<UserRs> editUser(UserRq request, String token) {
        UserRs response = new UserRs();
        Person person = personRepository.findByEmail(jwtTokenProvider.getUsername(token));
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setBirthDate(LocalDateTime.ofEpochSecond(request.getBirthDate(), 0, ZoneOffset.UTC));
        person.setPhone(request.getPhone());
        // фото
        person.setAbout(request.getAbout());
        person.setCityId(request.getTownId());
        person.setMessagesPermission(MessagesPermission.valueOf(request.getMessagesPermission()));

        return ResponseEntity.ok(response);
    }
}
