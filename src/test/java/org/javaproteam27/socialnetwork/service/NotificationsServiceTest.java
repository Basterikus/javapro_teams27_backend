package org.javaproteam27.socialnetwork.service;

import org.javaproteam27.socialnetwork.handler.exception.InvalidRequestException;
import org.javaproteam27.socialnetwork.model.entity.*;
import org.javaproteam27.socialnetwork.model.enums.NotificationType;
import org.javaproteam27.socialnetwork.repository.*;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.javaproteam27.socialnetwork.model.enums.NotificationType.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class NotificationsServiceTest {

    @Mock
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private LikeRepository likeRepository;

    private NotificationsService notificationsService;

    @Before
    public void setUp() {
        notificationsService = new NotificationsService(personService, personRepository, jwtTokenProvider,
                notificationRepository, friendshipRepository, postRepository, commentRepository, likeRepository);
    }

    @Test
    public void getNotifications() {
        String token = "token";
        Integer offset = 0;
        Integer perPage = 20;

        Post post = Post.builder().authorId(1).title("title").build();
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Testov");
        person.setPhoto("photo");
        Comment comment = Comment.builder().authorId(1).commentText("comment").build();
        Friendship friendship = new Friendship();
        friendship.setSrcPersonId(1);
        PostLike postLike = PostLike.builder().personId(1).build();

        List<Notification> expectedList = new ArrayList<>();
        expectedList.add(generateNotification(POST));
        expectedList.add(generateNotification(POST_COMMENT));
        expectedList.add(generateNotification(COMMENT_COMMENT));
        expectedList.add(generateNotification(FRIEND_REQUEST));
        expectedList.add(generateNotification(POST_LIKE));
        expectedList.add(generateNotification(COMMENT_LIKE));
        expectedList.add(generateNotification(MESSAGE));

        when(jwtTokenProvider.getUsername(token)).thenReturn("email");
        when(personRepository.findByEmail("email")).thenReturn(person);
        when(personRepository.findById(anyInt())).thenReturn(person);
        when(notificationRepository.findByPersonId(anyInt())).thenReturn(expectedList);
        when(postRepository.findPostById(anyInt())).thenReturn(post);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);
        when(friendshipRepository.findById(anyInt())).thenReturn(friendship);
        when(likeRepository.findById(anyInt())).thenReturn(postLike);

        var response = notificationsService.getNotifications(token, offset, perPage);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals("", response.getError());
        assertEquals(offset, response.getOffset());
        assertEquals(perPage, response.getPerPage());
        assertEquals(7, response.getData().size());
        assertEquals("title", response.getData().get(0).getInfo());
        assertEquals("comment", response.getData().get(1).getInfo());
        assertEquals("comment", response.getData().get(2).getInfo());
        assertEquals("Test Testov", response.getData().get(3).getInfo());
        assertEquals("Test Testov", response.getData().get(4).getInfo());
        assertEquals("Test Testov", response.getData().get(5).getInfo());
        assertNull(response.getData().get(6).getInfo());
        for (int i = 0; i < response.getData().size() - 1; i++) {
            assertEquals("Test", response.getData().get(i).getEntityAuthor().getFirstName());
        }
    }

    @Test
    public void emptyGetNotifications() {
        String token = "token";
        int offset = 0;
        int perPage = 20;

        List<Notification> expectedList = new ArrayList<>();
        expectedList.add(generateNotification(POST));
        expectedList.get(0).setRead(true);

        when(jwtTokenProvider.getUsername(token)).thenReturn("email");
        when(personRepository.findByEmail("email")).thenReturn(new Person());
        when(notificationRepository.findByPersonId(anyInt())).thenReturn(expectedList);

        var response = notificationsService.getNotifications(token, offset, perPage);

        assertEquals(0, response.getData().size());
    }

    @Test
    public void markAsReadAllNotifications() {
        String token = "token";
        Integer expectedOffset = 0;
        Integer expectedPerPage = 20;

        Comment comment = Comment.builder().authorId(1).build();
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Testov");
        person.setPhoto("photo");

        List<Notification> expectedList = new ArrayList<>();
        expectedList.add(generateNotification(POST_COMMENT));
        expectedList.add(generateNotification(COMMENT_COMMENT));

        when(jwtTokenProvider.getUsername(token)).thenReturn("email");
        when(personRepository.findByEmail("email")).thenReturn(person);
        when(personRepository.findById(anyInt())).thenReturn(person);
        when(notificationRepository.findByPersonId(anyInt())).thenReturn(expectedList);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);
        when(notificationRepository.findById(anyInt())).thenReturn(expectedList.get(0));

        var response = notificationsService.markAsReadNotification(token, 1, true);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals("", response.getError());
        assertEquals(expectedOffset, response.getOffset());
        assertEquals(expectedPerPage, response.getPerPage());
        assertEquals(2, response.getData().size());
        assertTrue(expectedList.get(0).isRead());
        assertTrue(expectedList.get(1).isRead());
        assertEquals(POST_COMMENT, response.getData().get(0).getNotificationType());
        assertEquals(COMMENT_COMMENT, response.getData().get(1).getNotificationType());
        verify(notificationRepository, times(2)).updateReadStatus(any());
    }

    @Test
    public void markAsReadOneNotification() {
        String token = "token";
        Integer expectedOffset = 0;
        Integer expectedPerPage = 1;

        Comment comment = Comment.builder().authorId(1).build();
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Testov");
        person.setPhoto("photo");

        Notification expectedNotification = generateNotification(POST_COMMENT);

        when(jwtTokenProvider.getUsername(token)).thenReturn("email");
        when(personRepository.findByEmail("email")).thenReturn(person);
        when(personRepository.findById(anyInt())).thenReturn(person);
        when(notificationRepository.findById(anyInt())).thenReturn(expectedNotification);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        var response = notificationsService.markAsReadNotification(token, 0, false);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals("", response.getError());
        assertEquals(expectedOffset, response.getOffset());
        assertEquals(expectedPerPage, response.getPerPage());
        assertEquals(1, response.getData().size());
        assertTrue(expectedNotification.isRead());
        assertEquals(POST_COMMENT, response.getData().get(0).getNotificationType());
        verify(notificationRepository, times(1)).updateReadStatus(any());
    }

    @Test
    public void markAsReadNotificationFail() {
        String token = "token";

        when(jwtTokenProvider.getUsername(token)).thenReturn("email");
        when(personRepository.findByEmail("email")).thenReturn(new Person());
        when(notificationRepository.findByPersonId(anyInt())).thenReturn(new ArrayList<>());

        InvalidRequestException thrown = assertThrows(InvalidRequestException.class,
                () -> notificationsService.markAsReadNotification(token, 1, true));

        assertEquals("Notifications not found", thrown.getMessage());
    }

    private Notification generateNotification(NotificationType notificationType) {
        return Notification.builder().isRead(false).sentTime(System.currentTimeMillis() - 10).entityId(1)
                .notificationType(notificationType).personId(1).build();
    }

    @Test
    public void createCommentNotification() {
        Post post = Post.builder().id(0).authorId(1).build();
        Person person = new Person();
        person.setId(5);
        Comment comment = Comment.builder().id(2).authorId(3).parentId(4).build();

        when(postRepository.findPostById(anyInt())).thenReturn(post);
        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createCommentNotification(post.getId(), System.currentTimeMillis(), comment.getId(),
                comment.getParentId());

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createCommentNotificationWithoutParentId() {
        Post post = Post.builder().id(0).authorId(1).build();
        Person person = new Person();
        person.setId(5);
        Comment comment = Comment.builder().id(2).authorId(3).build();

        when(postRepository.findPostById(anyInt())).thenReturn(post);
        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createCommentNotification(post.getId(), System.currentTimeMillis(), comment.getId(),
                comment.getParentId());

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createCommentNotificationWithSamePostAuthorAndPerson() {
        Post post = Post.builder().id(0).authorId(1).build();
        Person person = new Person();
        person.setId(1);
        Comment comment = Comment.builder().id(2).authorId(3).build();

        when(postRepository.findPostById(anyInt())).thenReturn(post);
        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createCommentNotification(post.getId(), System.currentTimeMillis(), comment.getId(),
                comment.getParentId());

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    public void createCommentNotificationWithSameCommentAuthorAndPostAuthor() {
        Post post = Post.builder().id(0).authorId(1).build();
        Person person = new Person();
        person.setId(3);
        Comment comment = Comment.builder().id(2).authorId(1).parentId(5).build();

        when(postRepository.findPostById(anyInt())).thenReturn(post);
        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createCommentNotification(post.getId(), System.currentTimeMillis(), comment.getId(),
                comment.getParentId());

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    public void createSubCommentNotification() {
        Comment comment = Comment.builder().id(2).authorId(3).parentId(4).build();
        Person person = new Person();
        person.setId(1);

        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);
        when(personService.getAuthorizedPerson()).thenReturn(person);

        notificationsService.createSubCommentNotification(comment.getParentId(), System.currentTimeMillis(),
                comment.getId());

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createSubCommentNotificationWithSameCommentAuthorAndPerson() {
        Comment comment = Comment.builder().id(2).authorId(1).parentId(4).build();
        Person person = new Person();
        person.setId(1);

        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);
        when(personService.getAuthorizedPerson()).thenReturn(person);

        notificationsService.createSubCommentNotification(comment.getParentId(), System.currentTimeMillis(),
                comment.getId());

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    public void createFriendshipNotification() {
        Friendship friendship = new Friendship();
        friendship.setId(1);

        when(friendshipRepository.findOneByIdAndFriendshipStatus(anyInt(), anyInt(), anyInt())).thenReturn(friendship);

        notificationsService.createFriendshipNotification(1, 2, 3);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createPostNotification() {
        Friendship friendship = new Friendship();
        friendship.setDstPersonId(1);

        List<Friendship> expectedList = List.of(friendship);

        when(friendshipRepository.findAllFriendsByPersonId(anyInt())).thenReturn(expectedList);

        notificationsService.createPostNotification(1, System.currentTimeMillis(), 2);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createPostLikeNotificationWhenTypePost() {
        Person person = new Person();
        person.setId(1);
        Post post = Post.builder().id(2).authorId(3).build();

        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(postRepository.findPostById(anyInt())).thenReturn(post);

        notificationsService.createPostLikeNotification(1, System.currentTimeMillis(), post.getId(), "Post");

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createPostLikeNotificationWhenTypePostWithSameLikeAuthorAndPerson() {
        Person person = new Person();
        person.setId(1);
        Post post = Post.builder().id(2).authorId(1).build();

        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(postRepository.findPostById(anyInt())).thenReturn(post);

        notificationsService.createPostLikeNotification(1, System.currentTimeMillis(), post.getId(), "Post");

        verify(notificationRepository, times(0)).save(any());
    }

    @Test
    public void createPostLIkeNotificationWhenTypeComment() {
        Person person = new Person();
        person.setId(1);
        Comment comment = Comment.builder().id(2).authorId(3).build();

        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createPostLikeNotification(1, System.currentTimeMillis(), comment.getId(),
                "Comment");

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    public void createPostLIkeNotificationWhenTypeCommentWithSameLikeAuthorAndPerson() {
        Person person = new Person();
        person.setId(1);
        Comment comment = Comment.builder().id(2).authorId(1).build();

        when(personService.getAuthorizedPerson()).thenReturn(person);
        when(commentRepository.getCommentById(anyInt())).thenReturn(comment);

        notificationsService.createPostLikeNotification(1, System.currentTimeMillis(), comment.getId(),
                "Comment");

        verify(notificationRepository, times(0)).save(any());
    }
}
