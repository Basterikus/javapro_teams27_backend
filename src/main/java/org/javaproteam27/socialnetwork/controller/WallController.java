package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostResponseDtoRs;
import org.javaproteam27.socialnetwork.service.PostDtoService;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/{id}/wall")
public class WallController {
    private final PostDtoService postDtoService;
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponseDtoRs> publishPost(
            @RequestParam(required = false) Long publish_date,
            @RequestBody PostDtoRq postDtoRq,
            @PathVariable(value = "id") int authorId){
        PostDtoRs postDtoRs = postService.publishPost(publish_date, postDtoRq, authorId);
        return ResponseEntity.ok(new PostResponseDtoRs("", postDtoRs,
                    null, postDtoRs.getTime())); //System.currentTimeMillis()
    }

    @GetMapping
    public ResponseEntity<ListResponseDtoRs> getUserPosts(
            @PathVariable(value = "id") int authorId,
            @RequestParam (defaultValue = "0") int offset,
            @RequestParam (defaultValue = "20") int itemPerPage) {
        List<PostDtoRs> data = postService.findAllUsersPosts(authorId)
                .stream().map(postDtoService::initialize)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponseDtoRs<>("", offset, itemPerPage, data));
    }
}
