package org.javaproteam27.socialnetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    @GetMapping("/me")
    public ResponseEntity<String> login(){
        return ResponseEntity.ok("Ok!");
    }
}
