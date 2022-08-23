package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
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

    @GetMapping
    public ResponseEntity<ListResponseDtoRs<PostDtoRs>> get(
            @RequestParam (name = "offset", defaultValue = "0") Integer offset,
            @RequestParam (name = "perPage", defaultValue = "20") Integer itemPerPage) {
        List<PostDtoRs> data = postService.findAllPosts()
                .stream().map(postDtoService::initialize)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponseDtoRs<>("", offset, itemPerPage, data));
    }
}
