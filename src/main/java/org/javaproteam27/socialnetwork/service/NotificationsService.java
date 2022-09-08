package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseRs;
import org.javaproteam27.socialnetwork.model.entity.Friendship;
import org.javaproteam27.socialnetwork.model.entity.Notification;
import org.javaproteam27.socialnetwork.model.entity.Person;
import org.javaproteam27.socialnetwork.model.enums.NotificationType;
import org.javaproteam27.socialnetwork.repository.FriendshipRepository;
import org.javaproteam27.socialnetwork.repository.NotificationRepository;
import org.javaproteam27.socialnetwork.repository.PersonRepository;
import org.javaproteam27.socialnetwork.repository.PostRepository;
import org.javaproteam27.socialnetwork.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationsService {

    private final PersonService personService;
    private final PersonRepository personRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final NotificationRepository notificationRepository;
    private final FriendshipRepository friendshipRepository;
    private final PostRepository postRepository;


    public ListResponseRs<NotificationBaseRs> getNotifications(String token, int offset, int itemPerPage) {
        String email = jwtTokenProvider.getUsername(token);
        Person person = personRepository.findByEmail(email);
        var notificationList = notificationRepository.findByPersonId(person.getId());
        var currentTime = System.currentTimeMillis();

        List<NotificationBaseRs> result = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (!notification.isRead() && notification.getSentTime() < currentTime) {
                result.add(getNotificationRs(notification));
            }
        }
        return new ListResponseRs<>("", offset, itemPerPage, result);
    }

    public ListResponseRs<NotificationBaseRs> markAsReadNotification(String token, int id, boolean all) {
        String email = jwtTokenProvider.getUsername(token);
        Person person = personRepository.findByEmail(email);
        var notificationList = notificationRepository.findByPersonId(person.getId());
        int itemPerPage = all ? 20 : 1;
        List<NotificationBaseRs> result = new ArrayList<>();
        if (all) {
            for (Notification notification : notificationList) {
                if (!notification.isRead()) {
                    notification.setRead(true);
                    notificationRepository.updateReadStatus(notification);
                    result.add(getNotificationRs(notification));
                }
            }
        } else {
            Notification notification = notificationRepository.findById(id);
            notification.setRead(true);
            notificationRepository.updateReadStatus(notification);
            result.add(getNotificationRs(notification));
        }
        return new ListResponseRs<>("", 0, itemPerPage, result);
    }

    public void createFriendshipNotification(int dstId, int friendshipStatusId, int srcId) {
        var friendship = friendshipRepository.findOneByIdAndFriendshipStatus(srcId,
                dstId, friendshipStatusId);
        var sentTime = System.currentTimeMillis();
        createNotification(dstId, NotificationType.FRIEND_REQUEST, friendship.getId(), sentTime);
    }

    public void createPostNotification(int authorId, Long publishDate, int postId) {
        var friendList = friendshipRepository.findAllFriendsByPersonId(authorId);
        for (Friendship friendship : friendList) {
            var friendId = friendship.getDstPersonId();
            createNotification(friendId, NotificationType.POST, postId, publishDate);
        }
    }

    private void createNotification(int dstId, NotificationType notificationType, int entityId, Long sentTime) {
        Notification notification = new Notification();
        notification.setSentTime(sentTime);
        notification.setPersonId(dstId);
        notification.setEntityId(entityId);
        notification.setNotificationType(notificationType);
        notification.setContact("");
        notification.setRead(false);
        notificationRepository.save(notification);
    }

    private NotificationBaseRs getNotificationRs(Notification notification) {
        return NotificationBaseRs.builder()
                .id(notification.getId())
                .info("String")
                .sentTime(notification.getSentTime())
                .notificationType(notification.getNotificationType())
                .entityId(notification.getEntityId())
                .build();
    }

}
