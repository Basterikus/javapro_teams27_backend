package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.CommentRq;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.CommentRs;
import org.javaproteam27.socialnetwork.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/post/{id}/comments")
public class CommentsController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ResponseRs<CommentRs>> createComment(@PathVariable(value = "id") int postId,
                                                               @RequestBody CommentRq commentRq){
        if (commentRq.getParentId() == null) {
            commentRq.setParentId(-1);
        }
        ResponseRs<CommentRs> responseRs = commentService.addComment(postId, commentRq.getCommentText(), commentRq.getParentId());
        return ResponseEntity.ok(responseRs);
    }

    @GetMapping
    public ResponseEntity<ListResponseRs<CommentRs>> getComments (@PathVariable(value = "id") int postId,
                                                                  @RequestParam (defaultValue = "0") int offset,
                                                                  @RequestParam (defaultValue = "20") int itemPerPage) {
        ListResponseRs<CommentRs> data = commentService.getCommentsByPostIdInResponse(postId, offset, itemPerPage);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ResponseRs<CommentRs>> deleteComment(@PathVariable(value = "id") int postId,
                                                               @PathVariable(value = "comment_id") int commentId){
        ResponseRs<CommentRs> responseRs = commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(responseRs);
    }
}
