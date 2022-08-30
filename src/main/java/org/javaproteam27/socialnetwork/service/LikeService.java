package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.LikeRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PersonService personService;

    public ResponseRs<LikeRs> addPostLike(String type, Integer postId){

        if (!type.equals("Post")){
            return null;
        }
        Long time = System.currentTimeMillis();
        Integer personId = personService.getAuthorizedPerson().getId();
        likeRepository.addPostLike(time, personId, postId);
        Integer likesCount = getCountByPostId(postId);
        List<Integer> userListLikedPost = likeRepository.getUserListLikedPost(postId);
        LikeRs data = LikeRs.builder().likes(likesCount).users(userListLikedPost).build();
        return new ResponseRs<>("", data, null);
    }

    public boolean deletePostLikeTest(Integer postId, Integer userId) {
        if (userId == null) {
            List<Integer> users = likeRepository.getUserListLikedPost(postId);
            users.forEach(user -> likeRepository.deletePostLike(postId, user));
        }
        return likeRepository.deletePostLike(postId, userId);
    }

    public ResponseRs<LikeRs> deletePostLike(String type, Integer postId) {

        if (!type.equals("Post")){
            return null;
        }
        Integer personId = personService.getAuthorizedPerson().getId();
        if (deletePostLikeTest(postId, personId)){
            LikeRs data = LikeRs.builder().likes(1).build();
            return new ResponseRs<>("", data, null);
        }
        return null;
    }

    public Integer getCountByPostId(Integer postId){

        return likeRepository.getLikesByPostId(postId).size();
    }

    public ResponseRs<LikeRs> getPostLikeList(String type, Integer postId) {

        if (!type.equals("Post")){
            return null;
        }
        List<Integer> likes = likeRepository.getUserListLikedPost(postId);
        LikeRs data = LikeRs.builder().likes(likes.size()).users(likes).build();
        return new ResponseRs<>("", data, null);
    }

    public Boolean isPostLikedByUser(Integer userId, Integer postId){

        List<Integer> likes = likeRepository.getLikesByPersonId(userId, postId);
        return !likes.isEmpty();
    }
}
