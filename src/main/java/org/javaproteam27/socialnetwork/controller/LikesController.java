package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.LikeRq;
import org.javaproteam27.socialnetwork.model.dto.response.LikeRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikesController {
    private final LikeService likeService;

    @PutMapping
    public ResponseEntity<ResponseRs<LikeRs>> putLike(@RequestBody LikeRq likeRq){
        ResponseEntity<ResponseRs<LikeRs>> retValue = null;
        if (likeRq.getType().equals("Post")){
            ResponseRs<LikeRs> responseRs = likeService.addPostLike(likeRq.getItemId());
            retValue = ResponseEntity.ok(responseRs);
        }
        return retValue;
    }

    @DeleteMapping
    public ResponseEntity<ResponseRs<LikeRs>> deleteLike(@RequestParam("item_id") Integer itemId,
                                                         @RequestParam String type){
        ResponseEntity<ResponseRs<LikeRs>> retValue = null;
        if (type.equals("Post")){
            ResponseRs<LikeRs> responseRs = likeService.deletePostLike(itemId);
            retValue = ResponseEntity.ok(responseRs);
        }
        return retValue;
    }

    @GetMapping
    public ResponseEntity<ResponseRs<LikeRs>> getLikeList(@RequestParam("item_id") Integer itemId,
                                                          @RequestParam String type){
        ResponseEntity<ResponseRs<LikeRs>> retValue = null;
        if (type.equals("Post")){
            ResponseRs<LikeRs> responseRs = likeService.getPostLikeList(itemId);
            retValue = ResponseEntity.ok(responseRs);
        }
        return retValue;
    }
}
