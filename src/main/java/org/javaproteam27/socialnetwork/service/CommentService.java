package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.CommentRs;
import org.javaproteam27.socialnetwork.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PersonService personService;
    private final CommentRepository commentRepository;

    public ResponseRs<CommentRs> addComment(int postId, String commentText, Integer parentId) {
        Integer authorId = personService.getAuthorizedPerson().getId();
        Long time = System.currentTimeMillis();
        Integer commentId = commentRepository.addComment(postId, commentText, parentId, authorId, time);
        parentId = (parentId == -1) ? commentId : parentId;
        CommentRs data = CommentRs.builder().id(commentId).commentText(commentText).postId(postId).authorId(authorId)
                .time(time).isBlocked(false).parentId(parentId).build();
        return new ResponseRs<>("", data, null);

    }

    public List<CommentRs> getCommentsByPostId(int postId) {
        return commentRepository.getAllCommentsByPostId(postId);
    }

    public ListResponseRs<CommentRs> getCommentsByPostIdInResponse(int postId, int offset, int itemPerPage) {
        List<CommentRs> data = getCommentsByPostId(postId);
        return new ListResponseRs<>("", offset, itemPerPage, data);
    }

    private Boolean deleteCommentTest(int postId, int commentId){
        return commentRepository.deleteComment(postId, commentId);
    }

    public ResponseRs<CommentRs> deleteComment(int postId, int commentId) {
        ResponseRs<CommentRs> responseRs = null;
        if (deleteCommentTest(postId, commentId)){
            responseRs = new ResponseRs<>("", CommentRs.builder().id(commentId).build(), null);
        }
        return responseRs;
    }

    public boolean deleteAllCommentsToPost(int postId) {
        List<CommentRs> comments = getCommentsByPostId(postId);
        if (comments.isEmpty())
            return true;
        return comments.stream().map(commentRs -> deleteCommentTest(postId, commentRs.getId())).
                reduce((c1, c2) -> (c1 && c2)).get();
    }
}
