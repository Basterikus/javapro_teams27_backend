package org.javaproteam27.socialnetwork.service;

import org.javaproteam27.socialnetwork.model.entity.PostComment;
import org.javaproteam27.socialnetwork.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentService {
    private PostCommentRepository postCommentRepository;

    @Autowired
    public PostCommentService(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    public List<PostComment> getPostCommentsByPostId(Integer postId){
        return postCommentRepository.getPostCommentsByPostId(postId);
    }
}
