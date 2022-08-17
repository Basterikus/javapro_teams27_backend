package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedsController {
    private final PostService postService;

    @GetMapping()
    public ResponseEntity<ListResponseDto<PostDto>> get(
            @RequestParam (name = "offset", defaultValue = "0") Integer offset,
            @RequestParam (name = "perPage", defaultValue = "20") Integer itemPerPage) {
        String error = "string";
        List<PostDto> data = postService.findAllPosts()
                .stream().map(postService::convertPostToPostDto)
                .collect(Collectors.toList());
        ListResponseDto<PostDto> postsResponseDto = new ListResponseDto<>(
                error,
                offset,
                itemPerPage,
                data);
        return ResponseEntity.ok(postsResponseDto);
    }
}
