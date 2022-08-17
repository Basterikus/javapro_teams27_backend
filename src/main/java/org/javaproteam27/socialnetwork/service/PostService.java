package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PersonDtoService personDtoService;

    public PostDto convertPostToPostDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .time(post.getTime())
                .title(post.getTitle())
                .isBlocked(post.getIsBlocked())
                .postText(post.getPostText())
                .author(personDtoService.initialize(personService.getPersonById(post.getAuthorId())))
                .postComments(postCommentService.getPostCommentsByPostId(post.getId()))
                .likes(postLikeService.getCountByPostId(post.getId())).build();
    }

    public List<Post> findAllPosts(){
        return postRepository.findAllPosts();
    }
}
