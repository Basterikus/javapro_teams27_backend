package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.request.PostRq;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.PostRs;
import org.javaproteam27.socialnetwork.model.dto.response.ResponseRs;
import org.javaproteam27.socialnetwork.model.entity.Post;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.javaproteam27.socialnetwork.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CommentService commentService;
    private final LikeService likeService;

    private final PersonService personService;


    private PostRs convertToPostRs(Post post){
        if (post == null) return null;
        long timestamp = post.getTime();
        String type = (timestamp > System.currentTimeMillis()) ? "QUEUED" : "POSTED";
        return PostRs.builder()
                .id(post.getId())
                .time(timestamp)//.toLocalDateTime())
                .author(personService.initialize(post.getAuthorId()))
                .title(post.getTitle())
                .likes(likeService.getCountByPostId(post.getId()))
                .tags(tagRepository.findTagsByPostId(post.getId()))
                .commentRs(commentService.getCommentsByPostId(post.getId()))
                .type(type)
                .postText(post.getPostText())
                .isBlocked(post.getIsBlocked()).myLike(false)
                .myLike(likeService.isPostLikedByUser(personService.getAuthorizedPerson().getId(), post.getId()))
                .build();
    }
    
    public ListResponseRs<PostRs> findAllPosts(int offset, int itemPerPage) {
        List<Post> posts = postRepository.findAllPublishedPosts();
        List<PostRs> data = (posts != null) ? posts.stream().map(this::convertToPostRs).
                collect(Collectors.toList()) : null;
        return new ListResponseRs<>("", offset, itemPerPage, data);
    }

    public ListResponseRs<PostRs> findAllUserPosts(int authorId, int offset, int itemPerPage) {
        List<Post> posts = postRepository.findAllUserPosts(authorId);
        List<PostRs> data = (posts != null) ? posts.stream().map(this::convertToPostRs).
                collect(Collectors.toList()) : null;
        return new ListResponseRs<>("", offset, itemPerPage, data);
    }

    public ResponseRs<PostRs> deletePost(int postId) {

        ResponseRs<PostRs> retValue = null;
        tagRepository.deleteTagsByPostId(postId);
        commentService.deleteAllCommentsToPost(postId);
        likeService.deletePostLikeTest(postId, null);
        if (postRepository.deletePostById(postId)) {
            retValue = new ResponseRs<>("", PostRs.builder().id(postId).build(),null);
        }
        return retValue;
    }

    public ResponseRs<PostRs> updatePost(int postId, String title, String postText, ArrayList<String> tags) {
        tagRepository.updateTagsPostId(postId, tags);
        postRepository.updatePostById(postId, title, postText);
        return new ResponseRs<>("", convertToPostRs(postRepository.findPostById(postId)),null);
    }

    public ResponseRs<PostRs> publishPost(Long publishDate, PostRq postRq, int authorId) {
        long publishDateTime = (publishDate == null) ? System.currentTimeMillis() : publishDate;
        int postId = postRepository.addPost(publishDateTime, authorId, postRq.getTitle(), postRq.getPostText());
        postRq.getTags().forEach(tag -> tagRepository.addTag(tag, postId));
        return (new ResponseRs<>("", convertToPostRs(postRepository.findPostById(postId)),null));
    }
}
