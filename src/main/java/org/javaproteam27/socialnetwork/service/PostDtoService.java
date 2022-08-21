package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.PostNotAddedException;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class PostDtoService {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PostAuthorDtoService postAuthorDtoService;
    private final PostService postService;

    public PostDtoRs initialize(Post post){
        Timestamp timestamp = new Timestamp(post.getTime());
        String type = (timestamp.after(new Timestamp(System.currentTimeMillis()))) ? "QUEUED" : "POSTED";
        return PostDtoRs.builder()
                .id(post.getId())
                .time(timestamp.toLocalDateTime())
                .author(postAuthorDtoService.initialize(personService.findById(post.getAuthorId())))
                .title(post.getTitle())
                .likes(postLikeService.getCountByPostId(post.getId()))
                .tags(new ArrayList<>())    //TODO: Add post2tag repository
                .postComments(postCommentService.getPostCommentsByPostId(post.getId()))
                .type(type)
                .postText(post.getPostText())
                .isBlocked(post.getIsBlocked()).myLike(false)
                .build();
    }

    public PostDtoRs publishPost(Long publishDateUet, PostDtoRq postDtoRq, Integer authorId) throws PostNotAddedException {
        LocalDateTime publishDateTime = (publishDateUet == null) ? LocalDateTime.now() :
                LocalDateTime.ofInstant(Instant.ofEpochMilli(publishDateUet),TimeZone.getDefault().toZoneId());
        int id = postService.addPost(publishDateTime, authorId, postDtoRq.getTitle(), postDtoRq.getPostText());
        String type = (publishDateUet == null) ? "POSTED" : "QUEUED";
        return PostDtoRs.builder()
                .id(id)
                .time(publishDateTime)
                .author(postAuthorDtoService.initialize(personService.findById(authorId)))
                .title(postDtoRq.getTitle())
                .likes(0)
                .tags(postDtoRq.getTags())
                .postComments(new ArrayList<>())
                .type(type)
                .postText(postDtoRq.getPostText())
                .isBlocked(false)
                .myLike(false).build();
    }
}
