package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostRs;
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
    public ResponseEntity<ListResponseRs<PostRs>> get(
            @RequestParam (name = "offset", defaultValue = "0") Integer offset,
            @RequestParam (name = "perPage", defaultValue = "20") Integer itemPerPage) {
        String error = "string";
        List<PostRs> data = postService.findAllPosts()
                .stream().map(postDtoService::initialize)
                .collect(Collectors.toList());
        ListResponseRs<PostRs> postsResponseDto = new ListResponseRs<>(
                error,
                offset,
                itemPerPage,
                data);
        return ResponseEntity.ok(postsResponseDto);
    }
}
