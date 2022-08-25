package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseDtoRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostDtoService postDtoService;

    public ResponseEntity<?> findAllPosts(int offset, int itemPerPage) {
        List<PostDtoRs> data = postRepository.findAllPublishedPosts().stream().
                map(postDtoService::initialize).collect(Collectors.toList());
        return ResponseEntity.ok(new ListResponseDtoRs<>("", offset, itemPerPage, data));
    }

    public ResponseEntity<?> findAllUserPosts(int authorId, int offset, int itemPerPage) {
        List<Post> posts = postRepository.findAllUserPosts(authorId);
        List<PostDtoRs> data = (posts != null) ? posts.stream().map(postDtoService::initialize).
                collect(Collectors.toList()) : null;
        return ResponseEntity.ok(new ListResponseDtoRs<>("", offset, itemPerPage, data));
    }

    public ResponseEntity<?> deletePost(int postId) {
        if (postRepository.deletePostById(postId)) {
            return ResponseEntity.ok(new ResponseDtoRs("", PostDtoRs.builder().id(postId).build(),
                    null));
        }
        return ResponseEntity.badRequest().body(new ResponseDtoRs("invalid_request",
                null, ""));
    }

    public ResponseEntity<?> updatePost(int postId, String title, String postText) {
        if (postRepository.updatePostById(postId, title, postText))
        {
            return ResponseEntity.ok(new ResponseDtoRs("", PostDtoRs.builder().id(postId).build(),
                        null));
        }
        else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    public ResponseEntity<?> publishPost(Long publishDate, PostDtoRq postDtoRq, int authorId) {
        long publishDateTime = (publishDate == null) ? System.currentTimeMillis() : publishDate;
        PostDtoRs postDtoRs;
        //LocalDateTime.ofInstant(Instant.ofEpochMilli(publishDateUet),TimeZone.getDefault().toZoneId());
        int id = postRepository.addPost(publishDateTime, authorId, postDtoRq.getTitle(), postDtoRq.getPostText());
        if (id >= 0) {
            postDtoRs = postDtoService.initialize(postRepository.findPostById(id));
            return ResponseEntity.ok(new ResponseDtoRs("", postDtoRs,
                    null)); //System.currentTimeMillis()
        }
        else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
