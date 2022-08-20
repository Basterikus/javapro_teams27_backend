package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.springframework.web.bind.annotation.*;

@RestController
public class WallController {
    @PostMapping("/api/v1/users/{id}/wall")
    public PostDtoRs publishPost(
            @RequestParam Long publish_date,
            @RequestBody PostDtoRq postDtoRq,
            @PathVariable String id){
//        Post.builder().
        return PostDtoRs.builder().build();
    }
}
