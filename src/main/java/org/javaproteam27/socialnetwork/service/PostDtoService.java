package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostDtoService {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PostAuthorDtoService postAuthorDtoService;

    public PostDtoRs initialize(Post post){
        Timestamp timestamp = new Timestamp(post.getTime());
        return PostDtoRs.builder()
                .id(post.getId())
                .time(timestamp.toLocalDateTime())
                .author(postAuthorDtoService.initialize(personService.findById(post.getAuthorId())))
                .title(post.getTitle())
                .likes(postLikeService.getCountByPostId(post.getId()))
                .tags(new ArrayList<>())    //TODO: Add post2tag repository
                .postComments(postCommentService.getPostCommentsByPostId(post.getId()))
                .type("POSTED")                //TODO: "POSTED???"
                .postText(post.getPostText())
                .isBlocked(post.getIsBlocked()).myLike(false)
                .build();
    }
}
