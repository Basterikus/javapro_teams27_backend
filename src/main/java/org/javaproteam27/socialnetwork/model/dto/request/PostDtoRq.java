package org.javaproteam27.socialnetwork.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.ArrayList;

@Value
public class PostDtoRq {
    String title;
    @JsonProperty("post_text")
    String postText;
    ArrayList <String> tags;
}
