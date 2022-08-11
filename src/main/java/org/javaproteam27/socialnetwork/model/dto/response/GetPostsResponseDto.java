package org.javaproteam27.socialnetwork.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
    /*{
            "error": "string",
            "timestamp": 1559751301818,
            "total": 0,
            "offset": 0,
            "perPage": 20,
            "data": [ PostDto postDto ]
    }*/
@Data
@AllArgsConstructor
public class GetPostsResponseDto {
    private String error;
    private Long timestamp;
    private Integer total;
    private Integer offset;
    private Integer perPage;
    private List<PostDto> data;
}
