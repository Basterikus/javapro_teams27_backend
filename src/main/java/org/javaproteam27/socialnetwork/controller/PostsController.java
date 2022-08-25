package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostsController {
    private final PostService postService;
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable(value = "id") int postId){
        return postService.deletePost(postId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable(value = "id") int postId,
                                                        @RequestBody PostDtoRq postDtoRq){
        //TODO: ADD TAGS!!!
        return postService.updatePost(postId, postDtoRq.getTitle(), postDtoRq.getPostText());
    }
}
