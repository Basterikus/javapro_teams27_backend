package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostRq;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

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
        //TODO: ADD TAGS!!!
        return postService.updatePost(postId, postRq.getTitle(), postRq.getPostText());
    }

    @GetMapping
    public ResponseEntity<?> findPost(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "dateFrom", required = false) Long dateFrom,
            @RequestParam(value = "dateTo", required = false) Long dateTo,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "perPage", required = false, defaultValue = "20") int itemPerPage) {

        return postService.findPost(text, dateFrom, dateTo, offset, itemPerPage);
    }
}
