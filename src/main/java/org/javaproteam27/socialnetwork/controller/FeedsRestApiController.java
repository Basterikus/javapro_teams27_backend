package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.FeedsDto;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedsRestApiController {
    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<ListResponseDto<PostDto>> get(@RequestBody FeedsDto feedsDto){
        String error = "errorString from DB???";
        Long timestamp = 0L;
        List<PostDto> data = postService.findPostsByPostText(feedsDto.getName())
                .stream().map(Post::convertToPostDto)
                .collect(Collectors.toList());
        ListResponseDto<PostDto> postsResponseDto = new ListResponseDto<>(error,
                timestamp,
                data.size(),
                feedsDto.getOffset(),
                feedsDto.getItemPerPage(),
                data);
        return ResponseEntity.ok(postsResponseDto);
    }
}
