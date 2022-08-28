package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostRq;
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
                                                        @RequestBody PostRq postRq){
        return postService.updatePost(postId, postRq.getTitle(), postRq.getPostText(), postRq.getTags());
    }

    /*@PostMapping("/{id}/comments")
    public ResponseEntity<ResponseRs<CommentRs>> createComment(@PathVariable(value = "id") int postId,
                                          @RequestBody CommentRq commentRq){
        ResponseRs<CommentRs> responseRs = postService.addComment(postId, commentRq.getCommentText(), commentRq.getParentId());
        return ResponseEntity.ok(responseRs);
    }*/
}
