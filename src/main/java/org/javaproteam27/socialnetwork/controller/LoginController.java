package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.aop.InfoLogger;
import org.javaproteam27.socialnetwork.model.dto.request.LoginRq;
import org.javaproteam27.socialnetwork.model.dto.response.LoginRs;
import org.javaproteam27.socialnetwork.model.dto.response.LogoutRs;
import org.javaproteam27.socialnetwork.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
@InfoLogger
public class LoginController {

    private final LoginService loginService;
    private final CaptchaService captchaService;

    @PostMapping("login")
    public LoginRs login(@RequestBody LoginRq loginRq) {
        return loginService.login(loginRq);
    }

    @PostMapping("logout")
    public LogoutRs logout() {
        return loginService.logout();
    }


    @GetMapping("/api/v1/auth/captcha")
    public CaptchaRs captcha() throws IOException {
        return captchaService.getCaptcha();
    }
}
