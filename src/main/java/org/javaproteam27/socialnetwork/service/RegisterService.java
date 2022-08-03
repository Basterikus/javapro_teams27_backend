package org.javaproteam27.socialnetwork.service;

import org.javaproteam27.socialnetwork.model.dto.request.RegisterRQ;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRS;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RegisterService {

    public ResponseEntity<RegisterRS> postRegister(RegisterRQ request) {

        RegisterRS registerRS = new RegisterRS();

        if (!request.getFirstName().matches(getRegularRusName()) ||
                !request.getFirstName().matches(getRegularEngName())) {
            registerRS.setError("Name");
            registerRS.setErrorDescription("Имя указано неверно");
            return new ResponseEntity<>(registerRS, HttpStatus.BAD_REQUEST);
        }

        if (!request.getLastName().matches(getRegularEngName()) ||
                !request.getLastName().matches(getRegularRusName())) {
            registerRS.setError("LastName");
            registerRS.setErrorDescription("Фамилия указано неверно");
            return new ResponseEntity<>(registerRS, HttpStatus.BAD_REQUEST);
        }
        if (!request.getEmail().matches(getRegularEmail())) {
            registerRS.setError("Email");
            registerRS.setErrorDescription("Email указано неверно");
            return new ResponseEntity<>(registerRS, HttpStatus.BAD_REQUEST);
        }
        if (request.getPasswd1().equals(request.getPasswd2())) {
            registerRS.setError("Password");
            registerRS.setErrorDescription("Пароли не совпадают");
            return new ResponseEntity<>(registerRS, HttpStatus.BAD_REQUEST);
        }

        registerRS.setError("string");
        registerRS.setTimestamp(System.currentTimeMillis());
        HashMap<String, String> data = new HashMap<>();
        data.put( "message","ok");
        registerRS.setData(data);
        return new ResponseEntity<>(registerRS, HttpStatus.OK);
    }

    public static String getRegularEngName() {
        return "[A-Z][a-z]+";
    }

    public static String getRegularRusName() {
        return "[А-Я][а-я]+";
    }

    public static String getRegularEmail() {
        return "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+";
    }
}
