package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDtoService {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PostAuthorDtoService postAuthorDtoService;
    private final TagRepository tagRepository;
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
                .tags(tagRepository.findTagsByPostId(post.getId()))
                .postComments(postCommentService.getPostCommentsByPostId(post.getId()))
                .type(type)
                .postText(post.getPostText())
                .isBlocked(post.getIsBlocked()).myLike(false)
                .build();
    }
}
