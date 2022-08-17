package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.service.PersonDtoService;
import org.javaproteam27.socialnetwork.service.PersonService;
import org.javaproteam27.socialnetwork.service.PostCommentService;
import org.javaproteam27.socialnetwork.service.PostLikeService;

@Data
@RequiredArgsConstructor
public class Post {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
    private final PersonDtoService personDtoService;
    private Integer id;
    private Long time;
    private Integer authorId;
    private String title;
    private String postText;
    private Boolean isBlocked;
    public PostDto convertToPostDto(){
        PostDto postDto = new PostDto();
        postDto.setId(id);
        postDto.setTime(time);
        postDto.setTitle(title);
        postDto.setIsBlocked(isBlocked);
        postDto.setAuthor(personDtoService.initialize(personService.getPersonById(authorId)));
        postDto.setPostComments(postCommentService.getPostCommentsByPostId(id));
        postDto.setLikes(postLikeService.getCountByPostId(id));
        return postDto;
    }
}
