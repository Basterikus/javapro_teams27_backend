package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post/{id}")
public class PostsController {
    private final PostService postService;
    @DeleteMapping
    public ResponseEntity<ResponseRs<PostRs>> deletePost(@PathVariable(value = "id") int postId){
        ResponseRs<PostRs> responseRs = postService.deletePost(postId);
        return ResponseEntity.ok(responseRs);
    }

    @PutMapping
    public ResponseEntity<ResponseRs<PostRs>> updatePost(@PathVariable(value = "id") int postId,
                                                        @RequestBody PostRq postRq){
        ResponseRs<PostRs> responseRs = postService.updatePost(postId, postRq.getTitle(),
                postRq.getPostText(), postRq.getTags());
        return ResponseEntity.ok(responseRs);
    }
}
