package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.PostNotAddedException;
import org.javaproteam27.socialnetwork.model.dto.request.PostDtoRq;
import org.javaproteam27.socialnetwork.model.dto.response.PostDtoRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostDtoService postDtoService;
    public int addPost(long time, int authorId, String title, String postText) throws PostNotAddedException {
        return postRepository.addPost(time, authorId, title, postText);
    }
    public List<Post> findAllPosts(){
        return postRepository.findAllPublishedPosts();
    }

    public List<Post> findAllUsersPosts(int authorId) {
        return postRepository.findAllUsersPosts(authorId);
    }

    public boolean deletePost(int postId) {
        return postRepository.deletePostById(postId);
    }

    public boolean updatePost(int postId, String title, String postText) {
        return postRepository.updatePostById(postId, title, postText);
    }

    public PostDtoRs findPostById(int postId) {
        Post post = postRepository.findPostById(postId);
        return postDtoService.initialize(post);
    }

    public PostDtoRs publishPost(Long publishDate, PostDtoRq postDtoRq, int authorId) {
        long publishDateTime = (publishDate == null) ? System.currentTimeMillis() : publishDate;
        //LocalDateTime.ofInstant(Instant.ofEpochMilli(publishDateUet),TimeZone.getDefault().toZoneId());
        try {
            int id = addPost(publishDateTime, authorId, postDtoRq.getTitle(), postDtoRq.getPostText());
            return findPostById(id);
        } catch (PostNotAddedException e) {
            return null;
        }
    }
}
