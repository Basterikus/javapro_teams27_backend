package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.PostNotAddedException;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostResponseDtoRs;
import org.javaproteam27.socialnetwork.service.PostDtoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WallController {
    private final PostDtoService postDtoService;
    @PostMapping("/api/v1/users/{id}/wall")
    public ResponseEntity<PostResponseDtoRs> publishPost(
            @RequestParam(required = false) Long publish_date,
            @RequestBody PostDtoRq postDtoRq,
            @PathVariable(value = "id") String authorId){
        try {
            PostDtoRs postDtoRs = postDtoService.publishPost(publish_date, postDtoRq, Integer.parseInt(authorId));
            return ResponseEntity.ok(new PostResponseDtoRs("string", postDtoRs, null, null));
        } catch (PostNotAddedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PostResponseDtoRs("invalid_request", null, e.getMessage(), null));
        }
    }
}
