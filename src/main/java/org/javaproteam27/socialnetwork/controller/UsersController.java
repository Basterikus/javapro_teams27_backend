package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.UserRq;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.model.dto.response.UserRs;
import org.javaproteam27.socialnetwork.service.LoginService;
import org.javaproteam27.socialnetwork.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;
    private final LoginService loginService;

    @GetMapping("/me")
    public PersonDto profileResponse(@RequestHeader("Authorization") String token) {
        return loginService.profileResponse(token);
    }

    @PutMapping("/me")
    public ResponseEntity<UserRs> editUser(UserRq request, @RequestHeader("Authorization") String token) {
        return userService.editUser(request, token);
    }
}
