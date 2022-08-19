package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.service.PostDtoService;
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
    private final PostDtoService postDtoService;

    @GetMapping()
    public ResponseEntity<ListResponseDto<PostDto>> get(
            @RequestParam (name = "offset", defaultValue = "0") Integer offset,
            @RequestParam (name = "perPage", defaultValue = "20") Integer itemPerPage) {
        String error = "string";
        List<PostDto> data = postService.findAllPosts()
                .stream().map(postDtoService::initialize)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponseDto<>(error, 0, 20, data));
        /*ListResponseDto<PostDto> postsResponseDto = new ListResponseDto<>();
        postsResponseDto.setError("string");
        postsResponseDto.setTimestamp(0L);
        postsResponseDto.setTotal(0);
        postsResponseDto.setOffset(0);
        postsResponseDto.setPerPage(0);
        postsResponseDto.setData(data);
        return (postsResponseDto);*/
    }
}
