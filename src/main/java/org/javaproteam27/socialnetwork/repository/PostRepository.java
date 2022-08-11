package org.javaproteam27.socialnetwork.repository;

import org.javaproteam27.socialnetwork.model.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    public List<Post> findPostsByPostText(String postText){
        List<Post> postList = new ArrayList<Post>();
        return postList;
    }
}
