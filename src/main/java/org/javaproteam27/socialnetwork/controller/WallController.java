package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id}/wall")
public class WallController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<?> publishPost(
            @RequestParam(required = false) Long publish_date,
            @RequestBody PostDtoRq postDtoRq,
            @PathVariable(value = "id") int authorId){
        return postService.publishPost(publish_date, postDtoRq, authorId);
    }

    @GetMapping
    public ResponseEntity<?> getUserPosts(
            @PathVariable(value = "id") int authorId,
            @RequestParam (defaultValue = "0") int offset,
            @RequestParam (defaultValue = "20") int itemPerPage) {
        return postService.findAllUserPosts(authorId, offset, itemPerPage);
    }
}