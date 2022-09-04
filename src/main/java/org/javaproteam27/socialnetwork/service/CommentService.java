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
        CommentRs data = CommentRs.builder().isDeleted(false).parentId(parentId).commentText(commentText).id(commentId).
                postId(postId).time(time).author(author).isBlocked(false).subComments(new ArrayList<>()).build();
        return new ResponseRs<>("", data, null);

    }

    public ResponseRs<CommentRs> editComment(int postId, int commentId, String commentText, Integer parentId){
        Person person = personService.getAuthorizedPerson();
        PersonRs author = PersonRs.builder().id(person.getId()).firstName(person.getFirstName()).
                lastName(person.getLastName()).photo(person.getPhoto()).build();
        Long time = System.currentTimeMillis();
        commentRepository.editComment(postId, commentId, commentText, time);
        CommentRs data = CommentRs.builder().isDeleted(false).parentId(parentId).commentText(commentText).id(commentId).
                postId(postId).time(time).author(author).isBlocked(false).subComments(new ArrayList<>()).build();
        return new ResponseRs<>("", data, null);
    }

    public ListResponseRs<CommentRs> getCommentsByPostIdInResponse(int postId, int offset, int itemPerPage) {
        return new ListResponseRs<>("", offset, itemPerPage, InitializeCommentsToPost(postId));
    }

    public boolean deleteAllCommentsToPost(int postId) {
        List<Comment> comments = commentRepository.getAllCommentsByPostId(postId);
        if (comments.isEmpty())
            return true;
        return comments.stream().map(comment -> deleteCommentTest(postId, comment.getId())).
                reduce((c1, c2) -> (c1 && c2)).get();
    }

    public ResponseRs<CommentRs> deleteComment(int postId, int commentId) {
        ResponseRs<CommentRs> responseRs = null;
        if (deleteCommentTest(postId, commentId)){
            responseRs = new ResponseRs<>("", CommentRs.builder().id(commentId).build(), null);
        }
        return responseRs;
    }

    private Boolean deleteCommentTest(int postId, int commentId){
        return commentRepository.deleteComment(postId, commentId);
    }

    public List<CommentRs> InitializeCommentsToPost(Integer postId){
        List<Comment> commentList = commentRepository.getAllCommentsByPostIdAndParentId(postId, null);
        List<CommentRs> commentRsList = commentList.stream().map(this::convertToCommentRs).collect(Collectors.toList());
        commentRsList.forEach(commentRs -> setSubCommentsToComments(commentRs, commentRs.getId()));
        return commentRsList;
    }

    private void setSubCommentsToComments (CommentRs commentRs, Integer parentId) {
        List<Comment> comments = commentRepository.getAllCommentsByPostIdAndParentId(commentRs.getPostId(), parentId);
        List<CommentRs> subComments = comments.stream().map(this::convertToCommentRs).collect(Collectors.toList());
        commentRs.setSubComments(subComments);
        commentRs.getSubComments().forEach(commentRs1 -> setSubCommentsToComments(commentRs1, commentRs1.getId()));
    }
}
