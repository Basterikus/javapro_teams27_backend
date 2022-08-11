package org.javaproteam27.socialnetwork.controller;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.GetPostsResponseDto;
import org.javaproteam27.socialnetwork.model.dto.response.PostDto;
import org.javaproteam27.socialnetwork.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostRestApiController {
    private final PostService postService;

    @GetMapping("/")
    public ResponseEntity<GetPostsResponseDto> getPosts(@RequestParam("text") String text,
                                                        @RequestParam(required = false,
                                                               name = "date_from") Integer dateFrom,
                                                        @RequestParam(required = false,
                                                                name = "date_to") Integer dateTo,
                                                        @RequestParam(required = false,
                                                                name = "offset") Integer offset,
                                                        @RequestParam(required = false,
                                                                name = "itemPerPage") Integer itemPerPage){
        /*if (dateFrom == null){
            ;
        }*/
        /*private String error;
        private String timestamp;
        private Integer total;
        private Integer offset;
        private Integer perPage;
        private List<PostDto> data;*/
        String error = "errorString from DB???";
        Long timestamp = 0L;
        List<PostDto> data = postService.findPostsByPostText(text)
                .stream().map(p -> p.convertToPostDto())
                .collect(Collectors.toList());
        GetPostsResponseDto postsResponseDto = new GetPostsResponseDto(error,
                timestamp,
                data.size(),
                offset,
                itemPerPage,
                data);
        return ResponseEntity.ok(postsResponseDto);
    }
}
