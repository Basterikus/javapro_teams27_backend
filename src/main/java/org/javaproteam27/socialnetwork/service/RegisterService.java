package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.RegisterRq;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PersonRepository personRepository;
    private String pass1;
    private String pass2;

    public ResponseEntity<RegisterRs> postRegister(RegisterRq request) {

        RegisterRs registerRS = new RegisterRs();
        checkPassword(request.getPasswd1(), request.getPasswd2());
        Person person = new Person();
        person.setEmail(request.getEmail());
        person.setFirstName(request.getFirstName());
        person.setLastName(request.getLastName());
        person.setRegDate(LocalDateTime.now());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        person.setPassword(passwordEncoder.encode(request.getPasswd1()));
        person.setIsApproved(true);  // добавить проверку почты
        personRepository.save(person);

        // добавить сохранение и проверку кода

        registerRS.setError("string");
        registerRS.setTimestamp(System.currentTimeMillis());
        HashMap<String, String> data = new HashMap<>();
        data.put( "message","ok");
        registerRS.setData(data);
        return new ResponseEntity<>(registerRS, HttpStatus.OK);
    }

    @AssertTrue
    private boolean checkPassword(String pass1, String pass2) {
        return pass1.equals(pass2);
    }


}
