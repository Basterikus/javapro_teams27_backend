package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.LoginFormDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostAuthLoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    //http://localhost:8086/api/v1/platform/languages

    @PostMapping("/login")
    public ResponseEntity<PostAuthLoginResponseDto> login(@RequestBody LoginFormDto loginForm){
        PostAuthLoginResponseDto postAuthLoginResponseDto = new PostAuthLoginResponseDto();
        postAuthLoginResponseDto.setExampleToLogin();
        return ResponseEntity.ok(postAuthLoginResponseDto);
    }

    //http://localhost:8086/api/v1/dialogs/unreaded
    //http://localhost:8086/api/v1/notifications
    //http://localhost:8086/api/v1/friends/recommendations?
    //http://localhost:8086/api/v1/feeds?
}
