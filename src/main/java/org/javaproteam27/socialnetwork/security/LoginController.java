package org.javaproteam27.socialnetwork.security;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JdbcTemplate jdbcTemplate;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/v1/auth/login")
    public String getToken(){
        AvailabilityUser availabilityUser = new AvailabilityUser(jdbcTemplate);
        String email = "asdasd@mail.ru";
        if (availabilityUser.checkUser(email)){
            System.out.println("true");
            return jwtTokenProvider.createToken(email);
        }
        return "it's not ok";
    }

    @GetMapping("/api/v1/test")
    public String test(){
        return "ok";
    }

}
