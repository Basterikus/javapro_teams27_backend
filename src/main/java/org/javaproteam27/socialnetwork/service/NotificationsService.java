package org.javaproteam27.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.javaproteam27.socialnetwork.model.dto.response.ListResponseRs;
import org.javaproteam27.socialnetwork.model.dto.response.NotificationBaseRs;
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
    
    private final NotificationService notificationService;
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

        List<NotificationBaseRs> result = new ArrayList<>();
        for (Notification notification : notificationList) {
            if (!notification.isRead()) {
                result.add(getNotificationRs(notification));
            }
        }
        return new ListResponseRs<>("", offset, itemPerPage, result);
    }

    public void createFriendshipNotification(int dstId, int friendshipStatusId) {
        var person = personService.getAuthorizedPerson();
        var friendship = friendshipRepository.findByFriendShipStatus(person.getId(),
                dstId, friendshipStatusId);
        createNotification(dstId, NotificationType.FRIEND_REQUEST, friendship.getId());
    }

    public void createPostNotification(int dstId, Long publishDate, int postId) {
        var date = Instant.ofEpochMilli(publishDate).atZone(ZoneOffset.UTC).toLocalDateTime();
        createNotification(dstId, NotificationType.POST, postId);
    }

//    public void createCommentOnPostNotification(int postId) {
//
//    }

    private void createNotification(int dstId, NotificationType notificationType, int entityId) {
        Notification notification = new Notification();
        notification.setSentTime(LocalDateTime.now());
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
                .sentTime(notification.getSentTime().atZone(ZoneOffset.UTC).toInstant().toEpochMilli())
                .notificationType(notification.getNotificationType())
                .entityId(notification.getEntityId())
                .build();
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
}
