package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.aop.InfoLogger;
import org.javaproteam27.socialnetwork.model.dto.request.UserRq;
import org.javaproteam27.socialnetwork.model.dto.response.PersonRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.UserRs;
import org.javaproteam27.socialnetwork.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.service.PersonService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@InfoLogger
public class UserController {

    private final PersonService personService;
    private final LoginService loginService;

    @GetMapping("/search")
    public ResponseEntity<ListResponseRs<PersonRs>> searchPeople(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "age_from", required = false) Integer ageFrom,
            @RequestParam(value = "age_to", required = false) Integer ageTo,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "perPage", required = false, defaultValue = "20") int itemPerPage) {

        return ResponseEntity.ok(personService.findPerson(firstName, lastName, ageFrom, ageTo, city, country,
                offset, itemPerPage));
    }

    @GetMapping("me")
    public ResponseEntity<ResponseRs<PersonRs>> profileResponse(@RequestHeader("Authorization") String token) {
        return loginService.profileResponse(token);
    }

    @PutMapping("/me")
    public ResponseEntity<UserRs> editUser(@RequestBody UserRq request, @RequestHeader("Authorization") String token) {
        return personService.editUser(request, token);
    }
}
