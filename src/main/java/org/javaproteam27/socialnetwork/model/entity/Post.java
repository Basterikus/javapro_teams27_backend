package org.javaproteam27.socialnetwork.model.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.CityDto;
import org.javaproteam27.socialnetwork.model.dto.response.CountryDto;
import org.javaproteam27.socialnetwork.model.dto.response.PersonDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.service.PersonService;
import org.javaproteam27.socialnetwork.service.PostCommentService;
import org.javaproteam27.socialnetwork.service.PostLikeService;

@Data
@RequiredArgsConstructor
public class Post {
    private final PersonService personService;
    private final PostCommentService postCommentService;
    private final PostLikeService postLikeService;
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
        Person postAuthor = personService.getPersonById(authorId);
        postDto.setAuthor(PersonDto.builder()
                .id(postAuthor.getId())
                .firstName(postAuthor.getFirstName())
                .lastName(postAuthor.getLastName())
                .regDate(postAuthor.getRegDate())
                .birthDate(postAuthor.getBirthDate())
                .email(postAuthor.getEmail())
                .phone(postAuthor.getPhone())
                .photo(postAuthor.getPhoto())
                .about(postAuthor.getAbout())
                .city(new CityDto(postAuthor.getCityId(), ""))
                .country(new CountryDto(1,""))
                .messagesPermission(postAuthor.getMessagesPermission())
                .lastOnlineTime(postAuthor.getLastOnlineTime())
                .isBlocked(postDto.getIsBlocked()).build());
        postDto.setPostComments(postCommentService.getPostCommentsByPostId(id));
        postDto.setLikes(postLikeService.getCountByPostId(id));
        return postDto;
    }
}
