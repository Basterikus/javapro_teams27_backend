package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.RegisterRq;
import org.javaproteam27.socialnetwork.model.dto.response.CaptchaRs;
import org.javaproteam27.socialnetwork.model.dto.response.RegisterRs;
import org.javaproteam27.socialnetwork.service.CaptchaService;
import org.javaproteam27.socialnetwork.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<RegisterRs> register(@RequestBody RegisterRq request) {
        return registerService.postRegister(request);
    }

}
