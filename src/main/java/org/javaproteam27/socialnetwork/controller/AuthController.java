package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.LoginFormDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostAuthLoginResponseDto;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    //http://localhost:8086/api/v1/platform/languages

    @PostMapping("/login")
    public ResponseEntity<PostAuthLoginResponseDto> login(@RequestBody LoginFormDto loginForm){
        PostAuthLoginResponseDto postAuthLoginResponseDto = new PostAuthLoginResponseDto();
        postAuthLoginResponseDto.setExampleToLogin(jwtTokenProvider.createToken(loginForm.getEmail()));
        return ResponseEntity.ok(postAuthLoginResponseDto);
    }

    //http://localhost:8086/api/v1/dialogs/unreaded
    //http://localhost:8086/api/v1/notifications
    //http://localhost:8086/api/v1/friends/recommendations?
    //http://localhost:8086/api/v1/feeds?
}
