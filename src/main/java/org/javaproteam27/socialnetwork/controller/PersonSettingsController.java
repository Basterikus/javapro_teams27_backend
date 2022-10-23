package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PersonSettingsRq;
import org.javaproteam27.socialnetwork.model.dto.response.PersonSettingsRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.service.PersonSettingsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
public class PersonSettingsController {

    private final PersonSettingsService personSettingsService;

    @GetMapping
    public ResponseRs<PersonSettingsRs> getPersonSettings() {
        return personSettingsService.getPersonSettings();
    }

    @PutMapping
    public ResponseRs<Object> editPersonSettings(@RequestBody PersonSettingsRq ps) {
        return personSettingsService.editPersonSettings(ps);
    }
}
