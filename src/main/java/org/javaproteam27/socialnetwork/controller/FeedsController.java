package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedsController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> get(
            @RequestParam (name = "offset", defaultValue = "0") Integer offset,
            @RequestParam (name = "perPage", defaultValue = "20") Integer itemPerPage) {
        return postService.findAllPosts(offset, itemPerPage);
    }
}
