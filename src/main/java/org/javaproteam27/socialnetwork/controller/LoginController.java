package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.CaptchaRs;
import org.javaproteam27.socialnetwork.model.dto.response.LoginRs;
import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutRs;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.service.CaptchaService;
import org.springframework.web.bind.annotation.*;
import org.javaproteam27.socialnetwork.service.LoginService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final CaptchaService captchaService;

    @PostMapping("/api/v1/auth/login")
    public LoginRs login(@RequestBody LoginRq loginRq) {
        return loginService.login(loginRq);
    }

    @PostMapping("/api/v1/auth/logout")
    public LogoutRs logout() {
        return loginService.logout();
    }


    @GetMapping("/api/v1/auth/captcha")
    public CaptchaRs captcha() throws IOException {
        return captchaService.getCaptcha();
    }
}
