package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PostDtoService {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PostAuthorDtoService postAuthorDtoService;
    //private final PostService postService;

    public PostRs initialize(Post post){
        if (post == null) return null;
        long timestamp = post.getTime();
        String type = (timestamp > System.currentTimeMillis()) ? "QUEUED" : "POSTED";
        return PostRs.builder()
                .id(post.getId())
                .time(timestamp)//.toLocalDateTime())
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
}
