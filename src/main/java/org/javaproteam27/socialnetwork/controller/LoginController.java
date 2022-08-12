package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.LoginRs;
import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutRs;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}
