package org.javaproteam27.socialnetwork.controller;

import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.springframework.web.bind.annotation.*;

@RestController
public class WallController {
    @PostMapping("/api/v1/users/{id}/wall")
    public PostDto publishPost(
            @RequestParam Long publish_date,
            @RequestBody PostDtoRq postDtoRq,
            @PathVariable String id){
//        Post.builder().
        return PostDto.builder().build();
    }
}
