package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostResponseDtoRs;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post")
public class PostsController {
    private final PostService postService;
    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDtoRs> deletePost(@PathVariable(value = "id") int postId){
        if (postService.deletePost(postId)) {
            return ResponseEntity.ok(new PostResponseDtoRs(""
                    , PostDtoRs.builder().id(postId).build()
                    ,""
                    , System.currentTimeMillis()));
        } else {
            return ResponseEntity.badRequest().body(new PostResponseDtoRs("invalid_request"
                    , null, "", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDtoRs> updatePost(@PathVariable(value = "id") int postId,
                                                        @RequestBody PostDtoRq postDtoRq){
        //TODO: ADD TAGS!!!
        if (postService.updatePost(postId, postDtoRq.getTitle(), postDtoRq.getPostText())) {
            return ResponseEntity.ok(new PostResponseDtoRs(""
                    , PostDtoRs.builder().id(postId).build()
                    ,""
                    , System.currentTimeMillis()));
        } else {
            return ResponseEntity.badRequest().body(new PostResponseDtoRs("invalid_request"
                    , null, "", null));
        }
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<PostResponseDtoRs> findPostById(@PathVariable(value = "id") int postId){
        PostDtoRs postDtoRs = postService.findPostById(postId);
        return ResponseEntity.ok(new PostResponseDtoRs(""
                , postDtoRs
                , null
                , System.currentTimeMillis()));
    }*/
}
