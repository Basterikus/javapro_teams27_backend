package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.handler.exception.PostNotAddedException;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public int addPost(LocalDateTime time, int authorId, String title, String postText) throws PostNotAddedException {
        return postRepository.addPost(time, authorId, title, postText);
    }
    public List<Post> findAllPosts(){
        return postRepository.findAllPosts();
    }
}
