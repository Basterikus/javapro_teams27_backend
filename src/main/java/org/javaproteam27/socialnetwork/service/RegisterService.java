package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.RegisterRQ;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRS;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.AssertTrue;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final PersonRepository personRepository;
    private String pass1;
    private String pass2;

    public ResponseEntity<RegisterRS> postRegister(RegisterRQ request) {

        RegisterRS registerRS = new RegisterRS();
        checkPassword(request.getPasswd1(), request.getPasswd2());
        Person person = new Person();
        person.setEmail(request.getEmail());
        person.setFirstName(request.getFirstName());
        person.setLastName(person.getLastName());
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
