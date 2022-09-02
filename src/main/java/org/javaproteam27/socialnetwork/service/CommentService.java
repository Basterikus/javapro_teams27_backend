package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.PersonRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.CommentRs;
import org.javaproteam27.socialnetwork.model.entity.Comment;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PersonService personService;
    private final CommentRepository commentRepository;

    private CommentRs convertToCommentRs(Comment comment) {
        Person person = personService.findById(comment.getAuthorId());
        PersonRs author = PersonRs.builder().id(person.getId()).firstName(person.getFirstName()).
                lastName(person.getLastName()).photo(person.getPhoto()).build();
        return CommentRs.builder().isDeleted(false).parentId(comment.getParentId()).
                commentText(comment.getCommentText()).id(comment.getId()).postId(comment.getPostId()).
                time(comment.getTime()).author(author).isBlocked(person.getIsBlocked()).subComments(new ArrayList<>()).
                build();
    }

    public ResponseRs<CommentRs> addComment(int postId, String commentText, Integer parentId) {
        Person person = personService.getAuthorizedPerson();
        PersonRs author = PersonRs.builder().id(person.getId()).firstName(person.getFirstName()).
                lastName(person.getLastName()).photo(person.getPhoto()).build();
        Long time = System.currentTimeMillis();
        Integer commentId = commentRepository.addComment(postId, commentText, parentId, author.getId(), time);
        parentId = (parentId == null) ? commentId : parentId;
        CommentRs data = CommentRs.builder().isDeleted(false).parentId(parentId).commentText(commentText).id(commentId).
                postId(postId).time(time).author(author).isBlocked(false).subComments(new ArrayList<>()).build();
        return new ResponseRs<>("", data, null);

    }

    public List<CommentRs> getCommentsByPostId(int postId) {
        List<Comment> comments = commentRepository.getAllCommentsByPostId(postId);
        return comments.stream().map(this::convertToCommentRs).collect(Collectors.toList());
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
