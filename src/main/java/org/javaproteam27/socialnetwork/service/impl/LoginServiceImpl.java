package org.javaproteam27.socialnetwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.InvalidRequestException;
import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.LoginDataRs;
import org.javaproteam27.socialnetwork.model.dto.response.LoginRs;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutDataRs;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutRs;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.security.AvailabilityUser;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.javaproteam27.socialnetwork.service.LoginService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JdbcTemplate jdbcTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginRs login(LoginRq loginRq) {
        String email = loginRq.getEmail();
        String password = loginRq.getPassword();
        Person person = jdbcTemplate.query("select * from person where email = ?",
                        new String[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
        if (person != null && person.getPassword().contains(password)) {
            String token = getToken(email);
            return new LoginRs("", new Date(), new LoginDataRs(person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getRegDate(),
                    person.getBirthDate(),
                    person.getEmail(),
                    person.getPhone(),
                    person.getPhoto(),
                    person.getAbout(),
                    person.getTown(),
                    person.getMessagesPermission(),
                    person.getLastOnlineTime(),
                    person.getIsBlocked(),
                    token));
        } else throw new InvalidRequestException("Incorrect email or password");
    }

    @Override
    public LogoutRs logout() {
        return new LogoutRs("", new Date(), new LogoutDataRs("ok"));
    }

    private String getToken(String email) {
        return jwtTokenProvider.createToken(email);
    }
}
