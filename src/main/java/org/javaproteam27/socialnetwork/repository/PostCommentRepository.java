package org.javaproteam27.socialnetwork.repository;

import org.javaproteam27.socialnetwork.model.entity.PostComment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostCommentRepository {
    public List<PostComment> getPostCommentsByPostId(Integer postId){
        List<PostComment> postCommentList = new ArrayList<>();
        return postCommentList;
    }
}
