package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.repository.PostLikeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    public Integer getCountByPostId(Integer postId){
        return postLikeRepository.countByPostId(postId);
    }
}