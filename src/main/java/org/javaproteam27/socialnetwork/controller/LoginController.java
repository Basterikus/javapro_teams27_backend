package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.javaproteam27.socialnetwork.service.LoginService;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/v1/auth/login")
    public LoginRs login(@RequestBody LoginRq loginRq) {
        return loginService.login(loginRq);
    }

    @PostMapping("/api/v1/auth/logout")
    public LogoutRs logout() {
        return loginService.logout();
    }

    @GetMapping("/api/v1/users/me")
    public ResponseEntity<ResponseDtoRs<PersonDto>> profileResponse(@RequestHeader("Authorization") String token) {
        return loginService.profileResponse(token);
    }
}
